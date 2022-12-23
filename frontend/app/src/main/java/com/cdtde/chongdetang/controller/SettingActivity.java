package com.cdtde.chongdetang.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdtde.chongdetang.R;

public class SettingActivity extends AppCompatActivity {
    private LinearLayout ziLiao,miMa,phone,zhuXiao,banQuan,banBen;
    private Button quit_btn;
    private TextView version_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        setListener();//设置监听事件
    }

    private void setListener() {
        ziLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "个人资料", Toast.LENGTH_SHORT).show();
            }
        });
        miMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "修改密码", Toast.LENGTH_SHORT).show();
            }
        });
        ziLiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "个人资料", Toast.LENGTH_SHORT).show();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "更换绑定手机号", Toast.LENGTH_SHORT).show();
            }
        });
        zhuXiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "注销账号", Toast.LENGTH_SHORT).show();
            }
        });
        banQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "版权声明", Toast.LENGTH_SHORT).show();
            }
        });
        banBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "当前已经是最高版本", Toast.LENGTH_SHORT).show();
            }
        });
        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "已退出", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initViews() {
        //findViewById
        ziLiao=findViewById(R.id.set_ziliao);
        miMa=findViewById(R.id.set_mima);
        phone=findViewById(R.id.set_shouji);
        zhuXiao=findViewById(R.id.set_zhuxiao);
        banQuan=findViewById(R.id.set_banquan);
        banBen=findViewById(R.id.set_banben);
        version_tv=findViewById(R.id.set_version_tv);

        quit_btn=findViewById(R.id.set_quit_btn);
    }



    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

}