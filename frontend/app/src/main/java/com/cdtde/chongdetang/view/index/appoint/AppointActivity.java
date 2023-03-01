package com.cdtde.chongdetang.view.index.appoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityAppointBinding;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.viewModel.my.AppointViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.util.Date;

public class AppointActivity extends AppCompatActivity {

    private ActivityAppointBinding binding;

    private AppointViewModel vm;

    private AppointDialog dialog;

    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_appoint);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true, false);
        vm = new ViewModelProvider(this).get(AppointViewModel.class);

        dialog = (AppointDialog) DialogUtil.create(this, AppointDialog.class, null);
        XPopup.Builder builder = new XPopup.Builder(this).dismissOnTouchOutside(false);
        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, builder);

        LiveEventBus.get("AppointDialog-appoint", Appointment.class)
                        .observe(this, appointment -> {
                            appointment.setOrderTime(new Date());
                            appointment.setState(Appointment.State.PROCESSING);
                            vm.addAppointment(appointment);
                            loading.show();
                        });

        LiveEventBus.get("MyRepository-requestAddAppointment", WebException.class)
                        .observe(this, e -> {
                            loading.smartDismiss();
                            if (e.isSuccess()) {
                                ToastUtils.showShort("预约成功！");
                            } else {
                                ToastUtils.showShort(e.getMessage());
                            }
                        });

        Glide.with(this)
                .load(R.drawable.content_banner)
                .into(binding.banner);

        Glide.with(this)
                .load(R.drawable.appoint_traffic)
                .into(binding.traffic);

        binding.appoint.setOnClickListener(v -> {
            if (vm.isLogin()) {
                dialog.show();
            } else {
                LoginActivity.actionStart(this);
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AppointActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}