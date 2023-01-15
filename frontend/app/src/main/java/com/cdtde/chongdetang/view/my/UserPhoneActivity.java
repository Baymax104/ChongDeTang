package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityUserPhoneBinding;
import com.cdtde.chongdetang.util.WindowUtil;

public class UserPhoneActivity extends AppCompatActivity {
    private ActivityUserPhoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_phone);
        binding.setLifecycleOwner(this);

        WindowUtil.initActivityWindow(binding.toolbar, this, true);
        setListener();//设置监听事件
    }

    private void setListener() {
        //按钮
        binding.newPhoneCommit.setOnClickListener(v -> {
            String phoneCode=binding.newPhoneEnterCodeEv.getText().toString();//用户输入内容
            if (phoneCode.isEmpty()){
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            }
            else{
                //检验验证码
                Toast.makeText(this, "验证码："+phoneCode, Toast.LENGTH_SHORT).show();
                finish();
            }

        });
        //获取验证码
        binding.newPhoneGetCodeTv.setOnClickListener(v -> {
            String phoneNumber=binding.newPhoneEnterPhoneEv.getText().toString();//新手机号
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            }
            else if (phoneNumber.length()!=11  || !phoneNumber.matches("[0-9]+")){
                Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            }
            else
                //发送验证码
                Toast.makeText(this, "向"+phoneNumber+"发送验证码", Toast.LENGTH_SHORT).show();

        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserPhoneActivity.class);
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
