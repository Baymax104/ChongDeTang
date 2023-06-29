package com.cdtde.chongdetang.view.index.appoint;

import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.BR;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BindingConfig;
import com.cdtde.chongdetang.base.view.DialogBinder;
import com.cdtde.chongdetang.base.vm.DialogScope;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.utils.DialogUtil;
import com.cdtde.chongdetang.utils.ValidateUtil;
import com.cdtde.chongdetang.requester.my.AppointRequester;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopupext.popup.TimePickerPopup;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 17:10
 * @Version 1
 */
public class AppointDialog extends BottomPopupView {

    private States states = DialogScope.getFromActivity(this, States.class);

    private TimePickerPopup timePicker =
            DialogUtil.createTimePicker(getContext(), states.appointment.getValue()::setOrderTime);

    private AppointRequester requester = DialogScope.getFromApplication(AppointRequester.class);


    public static class States extends StateHolder {
        public final State<Appointment> appointment = new State<>(new Appointment());
    }

    public class Handler {
        public final OnClickListener cancel = v -> dismiss();
        public final OnClickListener confirm = v -> {
            Appointment appoint = states.appointment.getValue();
            if (StringUtils.isEmpty(appoint.getSubscriber()) ||
                    StringUtils.isEmpty(appoint.getNumber()) ||
                    StringUtils.isEmpty(appoint.getPhone()) || appoint.getOrderTime() == null) {
                ToastUtils.showShort("输入不能为空");
            } else if (!ValidateUtil.validatePhone(appoint.getPhone())) {
                ToastUtils.showShort("手机号格式错误");
            } else {
                requester.addAppointment(appoint,
                        a -> {
                            ToastUtils.showShort("预约成功");
                            dismiss();
                        },
                        ToastUtils::showShort);
            }
        };

        public final OnClickListener selectDate = v -> timePicker.show();
    }

    public AppointDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_appoint;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        requester.registerObserver(DialogUtil.createNetLoading(getActivity()), this);
        DialogBinder.bind(this, new BindingConfig()
                .add(BR.state, states)
                .add(BR.handler, new Handler()));
    }
}
