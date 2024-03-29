package com.cdtde.chongdetang.view.my.login;

import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityLoginBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.requester.UserRequester;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.Starter;
import com.cdtde.chongdetang.utils.ValidateUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.view.my.register.RegisterActivity;
import com.cdtde.chongdetang.view.my.setting.userPassword.UserPasswordActivity;
import com.cdtde.chongdetang.view.my.setting.userPassword.UserPasswordFragment;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {


    @InjectScope(Scopes.APPLICATION)
    private UserRequester loginRequester;
    @InjectScope(Scopes.ACTIVITY)
    private States states;
    @InjectScope(Scopes.APPLICATION)
    private UserPasswordFragment.Messenger messenger;

    public static class States extends StateHolder {
        /**
         * 用户输入手机号
         */
        public final State<String> phone = new State<>("");
        /**
         * 用户输入密码
         */
        public final State<String> password = new State<>("");
    }

    public class Handler {
        public OnClickListener login = v -> {
            String phone = states.phone.getValue();
            String pwd = states.password.getValue();
            if (!ValidateUtil.validatePhone(phone) || !ValidateUtil.validatePassword(pwd)) {
                ToastUtils.showShort("手机号或密码格式错误！");
                return;
            }
            loginRequester.login(phone, pwd,
                    o -> finish(), ToastUtils::showShort);
        };

        public OnClickListener register = v ->
                Starter.actionStart(activity, RegisterActivity.class);

        public OnClickListener forget = v -> {
            if (StringUtils.isEmpty(states.phone.getValue())) {
                ToastUtils.showShort("请在输入框输入验证手机号");
            } else if (!ValidateUtil.validatePhone(states.phone.getValue())) {
                ToastUtils.showShort("手机号格式错误！");
            } else {
                UserStore.setPhone(states.phone.getValue());
                messenger.forgetEvent.send(true);
                Starter.actionStart(activity, UserPasswordActivity.class);
            }
        };

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