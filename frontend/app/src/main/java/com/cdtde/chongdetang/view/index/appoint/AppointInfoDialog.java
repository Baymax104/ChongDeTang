package com.cdtde.chongdetang.view.index.appoint;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.DialogAppointInfoBinding;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.viewModel.my.AppointViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/9 1:41
 * @Version 1
 */
public class AppointInfoDialog extends CenterPopupView {

    private DialogAppointInfoBinding binding;

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
        View view = getPopupImplView();
        view.setTag("layout/dialog_appoint_info_0");
        binding = DialogAppointInfoBinding.bind(view);
        binding.setLifecycleOwner(this);
        LiveEventBus.get("UserAppointmentActivity-onItemClick", Appointment.class)
                .observeSticky(this, binding::setAppointment);
    }
}
