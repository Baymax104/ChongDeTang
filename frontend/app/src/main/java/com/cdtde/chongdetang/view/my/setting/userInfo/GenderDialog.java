package com.cdtde.chongdetang.view.my.setting.userInfo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.User;
import com.lxj.xpopup.core.BottomPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/9 16:45
 * @Version 1
 */
public class GenderDialog extends BottomPopupView {

    private UserInfoActivity.Messenger messenger = DialogScope.getFromApplication(UserInfoActivity.Messenger.class);

    private States states = DialogScope.getFromActivity(this, States.class);

    public GenderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_user_gender;
    }

    public static class States extends StateHolder {
        public State<User> user = new State<>(new User());
    }

    public class Handler {
        public OnClickListener maleClick = v -> {
            states.user.getValue().setGender("男");
            messenger.userEvent.reply(states.user.getValue());
            dismiss();
        };

        public OnClickListener femaleClick = v -> {
            states.user.getValue().setGender("女");
            messenger.userEvent.reply(states.user.getValue());
            dismiss();
        };

        public OnClickListener cancel = v -> dismiss();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogBinder.bind(this, new BindingConfig()
                .add(BR.handler, new Handler()));
        messenger.userEvent.observeSend(this, true, states.user::setValue);
    }
}
