package com.cdtde.chongdetang.view.my.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivitySettingBinding;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.setting.userInfo.UserInfoActivity;
import com.cdtde.chongdetang.view.my.setting.userPassword.UserPasswordActivity;
import com.cdtde.chongdetang.view.my.setting.userPhone.UserPhoneActivity;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        binding.dataEntry.setOnClickListener(v -> UserInfoActivity.actionStart(this));

        binding.passwordEntry.setOnClickListener(v -> UserPasswordActivity.actionStart(this));

        binding.phoneEntry.setOnClickListener(v -> UserPhoneActivity.actionStart(this));

        binding.copyrightEntry.setOnClickListener(v -> Toast.makeText(this, "版权声明", Toast.LENGTH_SHORT).show());

        binding.versionEntry.setOnClickListener(v -> Toast.makeText(this, "当前已经是最高版本", Toast.LENGTH_SHORT).show());

        binding.logout.setOnClickListener(v -> {
            DialogUtil.create(this, LogoutDialog.class).show();
            finish();
        });

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