package com.cdtde.chongdetang.view.my.appoint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.AppointmentAdapter;
import com.cdtde.chongdetang.databinding.ActivityUserAppointBinding;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.AppointViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;


public class UserAppointActivity extends AppCompatActivity {
    private ActivityUserAppointBinding binding;

    private AppointViewModel vm;

    private LoadingPopupView loading;

    private AppointInfoDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_appoint);
        vm = new ViewModelProvider(this).get(AppointViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(vm);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        XPopup.Builder builder = new XPopup.Builder(this).dismissOnTouchOutside(false);
        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, builder).show();
        dialog = (AppointInfoDialog) DialogUtil.create(this, AppointInfoDialog.class, null);

        AppointmentAdapter adapter = new AppointmentAdapter();
        adapter.setOnItemClickListener(data -> {
            dialog.show();
            LiveEventBus.get("UserAppointmentActivity-onItemClick", Appointment.class)
                    .post(data);
        });

        binding.setAdapter(adapter);

        LiveEventBus.get("MyRepository-requestAllAppointment", WebException.class)
                .observe(this, e -> {
                    loading.smartDismiss();
                    if (e.isSuccess()) {
                        vm.refreshAllAppointment();
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });


    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserAppointActivity.class);
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
