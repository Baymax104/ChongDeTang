package com.cdtde.chongdetang.view.my.appoint;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.Appointment;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 1:41
 * @Version 1
 */
public class AppointInfoDialog extends CenterPopupView {


    private UserAppointActivity.Messenger messenger =
            DialogScope.getFromActivity(this, UserAppointActivity.Messenger.class);

    private States states = DialogScope.getFromActivity(this, States.class);

    public static class States extends StateHolder {
        public State<Appointment> appointment = new State<>(new Appointment());
    }

    public AppointInfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_appoint_info;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        DialogBinder.bind(this, new BindingConfig().add(BR.state, states));
        messenger.infoEvent.observeSend(this, true, states.appointment::setValue);
    }
}
