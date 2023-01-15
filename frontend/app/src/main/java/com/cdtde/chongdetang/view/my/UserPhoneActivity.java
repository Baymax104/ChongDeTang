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
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityUserPhoneBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.viewModel.my.UserPhoneViewModel;

public class UserPhoneActivity extends AppCompatActivity {
    private ActivityUserPhoneBinding binding;

    private UserPhoneViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_phone);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(UserPhoneViewModel.class);

        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        binding.setViewModel(vm);
        binding.setFragmentAdapter(new FragmentAdapter(this));

        binding.viewPager.setUserInputEnabled(false);
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
                    ToastUtils.showShort(vm.newPhone);
                }
            }
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
