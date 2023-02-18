package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityUserAppointBinding;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.AppointmentAdapter;
import com.cdtde.chongdetang.view.index.appoint.AppointInfoDialog;
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

        LiveEventBus.get("MyRepository-getAllAppointment", Boolean.class)
                .observe(this, aBoolean -> {
                    loading.smartDismiss();
                    if (aBoolean) {
                        vm.refreshAllAppointment();
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
