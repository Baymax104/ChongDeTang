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
import com.cdtde.chongdetang.databinding.ActivityRegisterBinding;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.viewModel.my.RegisterViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private RegisterViewModel vm;

    private LoadingPopupView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);
        vm = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setViewModel(vm);
        binding.setFragmentAdapter(new FragmentAdapter(this));

        binding.viewPager.setUserInputEnabled(false);

        XPopup.Builder builder = new XPopup.Builder(this).dismissOnTouchOutside(false);
        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, builder);

        LiveEventBus.get("MyRepository-register", Boolean.class)
                        .observe(this, aBoolean -> {
                            if (aBoolean) {
                                loading.smartDismiss();
                                finish();
                            }
                        });

        binding.confirm.setOnClickListener(v -> {
            if (vm.validate()) {
                vm.register();
                loading.show();
            } else {
                ToastUtils.showShort("验证码错误！");
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
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