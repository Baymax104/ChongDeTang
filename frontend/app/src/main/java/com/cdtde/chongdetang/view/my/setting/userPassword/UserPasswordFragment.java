package com.cdtde.chongdetang.view.my.setting.userPassword;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseFragment;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.FragmentUserPasswordBinding;
import com.cdtde.chongdetang.requester.my.PasswordValidateUseCase;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 0:09
 * @Version 1
 */
public class UserPasswordFragment extends BaseFragment<FragmentUserPasswordBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private PasswordValidateUseCase useCase;

    @InjectScope(Scopes.FRAGMENT)
    private States states;

    public static class States extends StateHolder {
        public State<String> oldPwd = new State<>("");
        public State<String> newPwd = new State<>("");
        public State<String> repeatPwd = new State<>("");
    }

    public class Handler {
        public void setOldPwd(@NonNull Editable s) {
            states.oldPwd.setValue(s.toString());
        }

        public void setNewPwd(@NonNull Editable s) {
            states.newPwd.setValue(s.toString());
        }

        public void setRepeatPwd(@NonNull Editable s) {
            states.repeatPwd.setValue(s.toString());
        }
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.fragment_user_password)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        useCase.validateEvent.observeSend(this, value -> {
            useCase.setOldPassword(states.oldPwd.getValue());
            useCase.setNewPassword(states.newPwd.getValue());
            useCase.setRepeatPassword(states.repeatPwd.getValue());
            useCase.validateEvent.reply(useCase.validatePassword());
        });
    }
}
