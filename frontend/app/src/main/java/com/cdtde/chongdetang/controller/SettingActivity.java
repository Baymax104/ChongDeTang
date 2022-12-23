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
//        binding.setData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SettingActivity.this, "个人资料", Toast.LENGTH_SHORT).show();
//            }
//        });
        /*
        lambda表达式是java8简化匿名内部类的语法，用于函数式接口，函数式接口就是一个接口内只有一个方法
        setOnClickListener()传入一个View.OnClickListener对象，OnClickListener是一个接口，里面只有OnClick一个方法
        可以用lambda表达式

        语法：setOnClickListener((参数1,参数2) -> {方法体});
        一个参数时可以省略括号，没有参数时不能省略括号
        () -> {方法体}
        参数1 -> {方法体}
        当方法体只有一行时，可以省略花括号，省略分号
        ((参数1,参数2) -> 方法体)
         */

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