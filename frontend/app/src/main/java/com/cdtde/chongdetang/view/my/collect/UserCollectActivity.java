package com.cdtde.chongdetang.view.my.collect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.databinding.ActivityUserCollectBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.UserCollectViewModel;

public class UserCollectActivity extends AppCompatActivity {

    private ActivityUserCollectBinding binding;

    private UserCollectViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_collect);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(UserCollectViewModel.class);
        binding.setViewModel(vm);
        binding.setAdapter(new FragmentAdapter(this));

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);
    }

    public static void actionStart(Context context) {
        Intent starter = new Intent(context, UserCollectActivity.class);
        context.startActivity(starter);
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