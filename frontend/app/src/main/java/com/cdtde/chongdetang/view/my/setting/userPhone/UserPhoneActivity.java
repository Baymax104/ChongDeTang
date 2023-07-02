package com.cdtde.chongdetang.view.my.setting.userPhone;

import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityUserPhoneBinding;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.setting.ValidateFragment;
import com.cdtde.chongdetang.useCase.PhoneValidateUseCase;
import com.cdtde.chongdetang.requester.UserInfoRequester;
import com.cdtde.chongdetang.useCase.ValidateUseCase;

import java.util.Arrays;
import java.util.List;

import kotlin.Unit;

public class UserPhoneActivity extends BaseActivity<ActivityUserPhoneBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private PhoneValidateUseCase phoneValidateUseCase;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.ACTIVITY)
    private ValidateUseCase validateUseCase;

    @InjectScope(Scopes.ACTIVITY)
    private UserInfoRequester requester;

    public static class States extends StateHolder {
        public State<Integer> page = new State<>(0);
        public List<Fragment> fragments = Arrays.asList(
                new ValidateFragment(),
                new UserPhoneFragment()
        );
    }

    public class Handler {
        public OnClickListener confirm = v -> {
            Integer page = states.page.getValue();
            if (page == 0) {
                validateUseCase.validateEvent.send(Unit.INSTANCE);
            } else if (page == 1) {
                phoneValidateUseCase.validateEvent.send(Unit.INSTANCE);
            }
        };
    }

    @Override
    protected ViewConfig configBinding() {
        requester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_user_phone)
                .add(BR.state, states)
                .add(BR.fragmentAdapter, new FragmentAdapter(this))
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityUserPhoneBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        binding.viewPager.setUserInputEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validateUseCase.validateEvent.observeReply(this, value -> {
            if (value) {
                states.page.setValue(1);
            } else {
                ToastUtils.showShort("验证码错误！");
            }
        });

        phoneValidateUseCase.validateEvent.observeReply(this, value -> {
            if (value) {
                requester.updatePhone(phoneValidateUseCase.getFinalPhone(),
                        o -> finish(), ToastUtils::showShort);
            } else {
                ToastUtils.showShort("验证码错误！");
            }
        });
    }
}
