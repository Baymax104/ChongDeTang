package com.cdtde.chongdetang.view.my.register;

import android.os.Bundle;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.MessageHolder;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityRegisterBinding;
import com.cdtde.chongdetang.requester.UserRequester;
import com.cdtde.chongdetang.useCase.ValidateUseCase;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.setting.ValidateFragment;

import java.util.Arrays;
import java.util.List;

import kotlin.Unit;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private UserRequester userRequester;

    @InjectScope(Scopes.ACTIVITY)
    private ValidateUseCase validateUseCase;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.ACTIVITY)
    private Messenger messenger;


    public static class States extends StateHolder {
        public State<Integer> page = new State<>(0);
        public List<Fragment> fragments = Arrays.asList(
                new RegisterFragment(),
                new ValidateFragment()
        );
    }

    public static class Messenger extends MessageHolder {
        public final Event<Integer, Unit> pageEvent = new Event<>();
    }

    public class Handler {
        public OnClickListener confirm = v -> validateUseCase.validateEvent.send(Unit.INSTANCE);
    }


    @Override
    protected ViewConfig configBinding() {
        userRequester.registerObserver(DialogUtil.createNetLoading(this), this);

        return new ViewConfig(R.layout.activity_register)
                .add(BR.state, states)
                .add(BR.fragmentAdapter, new FragmentAdapter(this))
                .add(BR.handler,new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityRegisterBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        binding.viewPager.setUserInputEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messenger.pageEvent.observeSend(this, states.page::setValue);

        validateUseCase.validateEvent.observeReply(this, value -> {
            if (value) {
                userRequester.register(o -> {
                    ToastUtils.showShort("注册成功！");
                    finish();
                }, ToastUtils::showShort);
            } else {
                ToastUtils.showShort("验证码错误！");
            }
        });

//        LiveEventBus.get("UserRepository-requestRegister", WebException.class)
//                        .observe(this, e -> {
//                            if (e.isSuccess()) {
//                                ToastUtils.showShort("注册成功！");
//                                finish();
//                            } else {
//                                ToastUtils.showShort(e.getMessage());
//                            }
//                        });
    }
}