package com.cdtde.chongdetang.view.my.register;

import android.os.Bundle;
import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

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
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 2:20
 * @Version 1
 */
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    @InjectScope(Scopes.ACTIVITY)
    private RegisterActivity.Messenger messenger;

    public static class States extends StateHolder {
        public State<String> phone = new State<>("");
        public State<String> pwd = new State<>("");
        public State<String> repeat = new State<>("");

        public State<Boolean> isPhoneValid = new State<>(true);
        public State<Boolean> isPwdValid = new State<>(true);
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
