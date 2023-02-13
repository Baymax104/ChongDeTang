package com.cdtde.chongdetang.view.index.appoint;

import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogAppointBinding;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.ValidateUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopupext.popup.TimePickerPopup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 17:10
 * @Version 1
 */
public class AppointDialog extends BottomPopupView {

    private Appointment appointment;

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
        DialogAppointBinding binding = DialogAppointBinding.bind(getPopupImplView());
        binding.setLifecycleOwner(this);
        appointment = new Appointment();
        binding.setAppointment(appointment);

        TimePickerPopup timePicker = new TimePickerPopup(getContext())
                .setTimePickerListener((DialogUtil.TimePickerListenerAdapter) (date, view) -> {
                    appointment.setDate(date);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    String d = format.format(date);
                    binding.dateEdit.setText(d);
                });

        binding.cancel.setOnClickListener(v -> dismiss());

        binding.selectDate.setOnClickListener(v ->
                DialogUtil.create(getContext(), timePicker, null).show()
        );

        binding.confirm.setOnClickListener(v -> {
            String subscriber = appointment.getSubscriber();
            String phone = appointment.getPhone();
            String number = appointment.getNumber();
            Date date = appointment.getDate();

            if (subscriber == null || subscriber.equals("") ||
                    number == null || number.equals("") ||
                    phone == null || phone.equals("") || date == null) {
                ToastUtils.showShort("输入不能为空");
            } else if (!ValidateUtil.validatePhone(phone)) {
                ToastUtils.showShort(phone);
            } else {
                LiveEventBus.get("AppointDialog-appoint", Appointment.class)
                        .post(appointment);
                dismiss();
            }
        });
    }
}
