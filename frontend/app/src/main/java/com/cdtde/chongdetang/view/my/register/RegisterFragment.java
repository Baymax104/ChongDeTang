package com.cdtde.chongdetang.view.my.register;

import android.os.Bundle;
import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentRegisterBinding;
import com.cdtde.chongdetang.repository.UserStore;
import com.cdtde.chongdetang.utils.ValidateUtil;

/**
 * 注册信息填写页
 */
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.ACTIVITY)
    private RegisterActivity.Messenger messenger;

    public static class States extends StateHolder {
        /**
         * 输入手机号
         */
        public State<String> phone = new State<>("");
        /**
         * 输入密码
         */
        public State<String> pwd = new State<>("");
        /**
         * 重复输入密码
         */
        public State<String> repeat = new State<>("");
        /**
         * 手机号是否合法
         */
        public State<Boolean> isPhoneValid = new State<>(true);
        /**
         * 密码是否合法
         */
        public State<Boolean> isPwdValid = new State<>(true);
        /**
         * 重复输入是否合法
         */
        public State<Boolean> isRepeatValid = new State<>(true);
    }

    public class Handler {
        public void setPhone(Editable s) {
            states.phone.setValue(s.toString());
        }

        public void setPassword(Editable s) {
            states.pwd.setValue(s.toString());
        }

        public void setRepeat(Editable s) {
            states.repeat.setValue(s.toString());
        }

        public OnClickListener register = v -> {
            boolean isPhoneValid = ValidateUtil.validatePhone(states.phone.getValue());
            boolean isPwdValid = ValidateUtil.validatePassword(states.pwd.getValue());
            boolean isRepeatValid = ValidateUtil.validateRepeat(states.pwd.getValue(),states.repeat.getValue());
            states.isPhoneValid.setValue(isPhoneValid);
            states.isPwdValid.setValue(isPwdValid);
            states.isRepeatValid.setValue(isRepeatValid);
            if (isPhoneValid && isPwdValid && isRepeatValid) {
                messenger.pageEvent.send(1);
                UserStore.setPhone(states.phone.getValue());
                UserStore.setPassword(states.pwd.getValue());
            }
        };
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_register)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
