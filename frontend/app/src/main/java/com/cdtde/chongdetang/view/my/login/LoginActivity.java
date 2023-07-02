package com.cdtde.chongdetang.view.my.login;

import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityLoginBinding;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.ValidateUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.register.RegisterActivity;
import com.cdtde.chongdetang.requester.UserRequester;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {


    @InjectScope(Scopes.APPLICATION)
    private UserRequester loginRequester;

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    public static class States extends StateHolder {
        public final State<String> phone = new State<>("");
        public final State<String> password = new State<>("");
    }

    public class Handler {
        public OnClickListener login = v -> {
            String phone = states.phone.getValue();
            String pwd = states.password.getValue();
            if (!ValidateUtil.validatePhone(phone) || !ValidateUtil.validatePassword(pwd)) {
                ToastUtils.showShort("手机号或密码错误！");
                return;
            }
            loginRequester.login(phone, pwd,
                    o -> finish(), ToastUtils::showShort);
        };

        public OnClickListener register = v ->
                Starter.actionStart(activity, RegisterActivity.class);

        public void setPhone(Editable s) {
            states.phone.setValue(s.toString());
        }

        public void setPassword(Editable s) {
            states.password.setValue(s.toString());
        }
    }

    @Override
    protected ViewConfig configBinding() {
        loginRequester.registerObserver(DialogUtil.createNetLoading(this), this);
        return new ViewConfig(R.layout.activity_login)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActivityLoginBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }
}