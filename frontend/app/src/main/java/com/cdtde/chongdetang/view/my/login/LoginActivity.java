package com.cdtde.chongdetang.view.my.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityLoginBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.register.RegisterActivity;
import com.cdtde.chongdetang.viewModel.my.LoginViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private LoginViewModel vm;

    private LoadingPopupView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(vm);
        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        XPopup.Builder builder = new XPopup.Builder(this)
                .dismissOnTouchOutside(false);
        loadingView = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, builder);

        LiveEventBus.get("MyRepository-requestLogin", WebException.class)
                        .observe(this, e -> {
                            loadingView.smartDismiss();
                            if (e.isSuccess()) {
                                LiveEventBus.get("User-isLogin", Boolean.class).post(true);
                                finish();
                            } else {
                                ToastUtils.showShort(e.getMessage());
                            }
                        });

        binding.login.setOnClickListener(v -> {
            boolean isValid = vm.validate();
            if (!isValid) {
                ToastUtils.showShort("???????????????????????????");
                return;
            }
            vm.login();
            loadingView.show();
        });

        binding.registerEntry.setOnClickListener(v -> RegisterActivity.actionStart(this));
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