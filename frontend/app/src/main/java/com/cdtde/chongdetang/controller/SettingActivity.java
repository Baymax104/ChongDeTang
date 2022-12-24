package com.cdtde.chongdetang.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        setListener();//设置监听事件
    }

    private void setListener() {
        binding.setData.setOnClickListener(v -> Toast.makeText(this, "个人资料", Toast.LENGTH_SHORT).show());
        binding.setPassword.setOnClickListener(v -> Toast.makeText(this, "修改密码", Toast.LENGTH_SHORT).show());
        binding.setPhone.setOnClickListener(v -> Toast.makeText(this, "更换绑定手机号", Toast.LENGTH_SHORT).show());
        binding.logoff.setOnClickListener(v -> Toast.makeText(this, "注销账号", Toast.LENGTH_SHORT).show());
        binding.copyright.setOnClickListener(v -> Toast.makeText(this, "版权声明", Toast.LENGTH_SHORT).show());
        binding.version.setOnClickListener(v -> Toast.makeText(this, "当前已经是最高版本", Toast.LENGTH_SHORT).show());
    }

    private void initViews() {

        setSupportActionBar(binding.settingsToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

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