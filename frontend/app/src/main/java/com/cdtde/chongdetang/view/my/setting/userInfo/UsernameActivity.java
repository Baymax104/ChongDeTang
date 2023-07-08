package com.cdtde.chongdetang.view.my.setting.userInfo;

import android.os.Bundle;
import android.text.Editable;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.InjectScope;
import com.cdtde.chongdetang.base.vm.Scopes;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActicityUsernameBinding;
import com.cdtde.chongdetang.entity.User;
import com.cdtde.chongdetang.utils.ValidateUtil;
import com.cdtde.chongdetang.utils.WindowUtil;

public class UsernameActivity extends BaseActivity<ActicityUsernameBinding> {

    @InjectScope(Scopes.ACTIVITY)
    private States states;

    @InjectScope(Scopes.APPLICATION)
    private UserInfoActivity.Messenger messenger;

    public static class States extends StateHolder {
        public State<Boolean> validity = new State<>(true);
        public State<String> content = new State<>("");
        public State<User> user = new State<>(new User());
    }

    public class Handler {
        public OnClickListener confirm = v -> {
            String content = states.content.getValue();
            if (ValidateUtil.validateUsername(content)) {
                states.user.getValue().setUsername(content);
                messenger.userEvent.reply(states.user.getValue());
                finish();
            } else {
                states.validity.setValue(false);
            }
        };

        public void setContent(Editable s) {
            states.content.setValue(s.toString());
        }
    }

    @Override
    protected ViewConfig configBinding() {
        return new ViewConfig(R.layout.acticity_username)
                .add(BR.state, states)
                .add(BR.handler, new Handler());
    }

    @Override
    protected void initUIComponent(@NonNull ActicityUsernameBinding binding) {
        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messenger.userEvent.observeSend(this, true, states.user::setValue);
    }
}
