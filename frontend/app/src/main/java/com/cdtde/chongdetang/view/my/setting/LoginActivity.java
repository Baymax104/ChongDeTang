package com.cdtde.chongdetang.view.my.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityLoginBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(vm);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        binding.login.setOnClickListener(v -> {
            boolean isValid = vm.validate();
            if (!isValid) {
                ToastUtils.showShort("用户名或密码错误！");
                return;
            }
            boolean isLogin = vm.login();
            if (!isLogin) {
                ToastUtils.showShort("用户名或密码错误");
                return;
            }
            finish();
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
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