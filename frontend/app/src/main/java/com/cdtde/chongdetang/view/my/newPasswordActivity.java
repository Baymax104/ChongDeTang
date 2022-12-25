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

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityNewPasswordBinding;

public class newPasswordActivity extends AppCompatActivity {
    private ActivityNewPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        setListener();//设置监听事件
    }

    private void setListener() {
        //按钮
        binding.newPasswordCommit.setOnClickListener(v -> {
            String oloPassword=binding.newPasswordOldEv.getText().toString();//旧密码
            String newPassword1=binding.newPasswordNew1Ev.getText().toString();//新密码1
            String newPassword2=binding.newPasswordNew2Ev.getText().toString();//新密码2
            if (oloPassword.isEmpty() || newPassword1.isEmpty() || newPassword2.isEmpty()){
                Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
            }
            //检查旧密码--->数据库
            else if (!oloPassword.equals(oloPassword)){
                Toast.makeText(this, "旧密码输入错误", Toast.LENGTH_SHORT).show();
            }
            //比较新密码
            else if (!newPassword1.equals(newPassword2)){
                Toast.makeText(this, "两次输的新密码不一致", Toast.LENGTH_SHORT).show();
            }
            //比较新旧密码
            else if (!newPassword1.equals(oloPassword)){
                Toast.makeText(this, "新密码不得与旧密码相同", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, newPasswordActivity.class);
        context.startActivity(intent);
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
