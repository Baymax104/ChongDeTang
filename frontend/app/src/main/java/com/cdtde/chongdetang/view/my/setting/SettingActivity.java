package com.cdtde.chongdetang.view.my.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivitySettingBinding;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.setting.userInfo.UserInfoActivity;
import com.cdtde.chongdetang.view.my.setting.userPassword.UserPasswordActivity;
import com.cdtde.chongdetang.view.my.setting.userPhone.UserPhoneActivity;
import com.cdtde.chongdetang.viewModel.my.SettingViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    private SettingViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(SettingViewModel.class);
        binding.setViewModel(vm);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        LiveEventBus.get("LogoutDialog-logout", Boolean.class)
                        .observe(this, aBoolean -> {
                            if (aBoolean) {
                                finish();
                            }
                        });


        binding.dataEntry.setOnClickListener(v -> {
            if (vm.getUser().getToken() != null) {
                UserInfoActivity.actionStart(this);
            } else {
                LoginActivity.actionStart(this);
            }
        });

        binding.passwordEntry.setOnClickListener(v -> {
            if (vm.getUser().getToken() != null) {
                UserPasswordActivity.actionStart(this);
            } else {
                LoginActivity.actionStart(this);
            }
        });

        binding.phoneEntry.setOnClickListener(v -> {
            if (vm.getUser().getToken() != null) {
                UserPhoneActivity.actionStart(this);
            } else {
                LoginActivity.actionStart(this);
            }
        });

        binding.copyrightEntry.setOnClickListener(v -> Toast.makeText(this, "版权声明", Toast.LENGTH_SHORT).show());

        binding.versionEntry.setOnClickListener(v -> Toast.makeText(this, "当前已经是最高版本", Toast.LENGTH_SHORT).show());

        binding.logout.setOnClickListener(v -> DialogUtil.create(this, LogoutDialog.class, null).show());

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
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