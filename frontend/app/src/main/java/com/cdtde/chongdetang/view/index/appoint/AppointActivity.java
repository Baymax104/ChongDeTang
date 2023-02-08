package com.cdtde.chongdetang.view.index.appoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityAppointBinding;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.setting.LoginActivity;
import com.cdtde.chongdetang.viewModel.index.AppointViewModel;

public class AppointActivity extends AppCompatActivity {

    private ActivityAppointBinding binding;

    private AppointViewModel vm;

    private AppointDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_appoint);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);
        vm = new ViewModelProvider(this).get(AppointViewModel.class);

        dialog = (AppointDialog) DialogUtil.create(this, AppointDialog.class, null);
        

        Glide.with(this)
                .load(R.drawable.content_banner)
                .into(binding.banner);

        Glide.with(this)
                .load(R.drawable.appoint_traffic)
                .into(binding.traffic);

        binding.appoint.setOnClickListener(v -> {
            if (vm.isLogin()) {
                ToastUtils.showShort("预约");
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