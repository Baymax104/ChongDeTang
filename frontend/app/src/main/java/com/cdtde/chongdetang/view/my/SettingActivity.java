package com.cdtde.chongdetang.view.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

        binding.dataEntry.setOnClickListener(v -> {
            Toast.makeText(this, "个人资料", Toast.LENGTH_SHORT).show();
            PersonInfoActivity.actionStart(this);
        });
        binding.passwordEntry.setOnClickListener(v -> {
            Toast.makeText(this, "修改密码", Toast.LENGTH_SHORT).show();
            NewPasswordActivity.actionStart(this);
        });
        binding.phoneEntry.setOnClickListener(v -> {
            Toast.makeText(this, "更换绑定手机号", Toast.LENGTH_SHORT).show();
            NewPhoneActivity.actionStart(this);
        });
        binding.logoffEntry.setOnClickListener(v -> {
            Toast.makeText(this, "注销账号", Toast.LENGTH_SHORT).show();
            reconfirm();
        });
        binding.copyrightEntry.setOnClickListener(v -> Toast.makeText(this, "版权声明", Toast.LENGTH_SHORT).show());
        binding.versionEntry.setOnClickListener(v -> Toast.makeText(this, "当前已经是最高版本", Toast.LENGTH_SHORT).show());
        binding.setQuitBtn.setOnClickListener(v -> {
            Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    public void reconfirm() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("注销提示");
        builder.setMessage("确定注销账号吗？此操作不可逆！！！");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            //点击确定执行
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(SettingActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
            }});
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener(){
            //点击取消执行
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(SettingActivity.this, "取消注销", Toast.LENGTH_SHORT).show();
            }});
        AlertDialog b = builder.create();
        b.show();//显示对话框
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