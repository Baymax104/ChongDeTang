package com.cdtde.chongdetang.view.my.setting.userPassword;

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
import com.cdtde.chongdetang.databinding.ActivityUserPasswordBinding;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.viewModel.my.UserPasswordViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class UserPasswordActivity extends AppCompatActivity {
    private ActivityUserPasswordBinding binding;

    private UserPasswordViewModel vm;

    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_password);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(UserPasswordViewModel.class);

        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        XPopup.Builder builder = new XPopup.Builder(this).dismissOnTouchOutside(false);
        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, builder);

        binding.setViewModel(vm);
        binding.setFragmentAdapter(new FragmentAdapter(this));
        binding.viewPager.setUserInputEnabled(false);

        LiveEventBus.get("MyRepository-updatePassword", Boolean.class)
                        .observe(this, aBoolean -> {
                            loading.smartDismiss();
                            if (aBoolean) {
                                finish();
                            }
                        });

        binding.confirm.setOnClickListener(v -> {
            Integer page;
            if ((page = vm.getPage().getValue()) != null) {
                if (page == 1) {
                    if (vm.validate()) {
                        vm.setPage(2);
                    } else {
                        ToastUtils.showShort("验证码错误！");
                    }
                } else if (page == 2) {
                    String msg = vm.setUserPassword();
                    if ("OK".equals(msg)) {
                        vm.updatePassword();
                        loading.show();
                    } else {
                        ToastUtils.showShort(msg);
                    }
                }
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserPasswordActivity.class);
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
