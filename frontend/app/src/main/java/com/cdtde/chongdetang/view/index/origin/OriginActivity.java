package com.cdtde.chongdetang.view.index.origin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityOriginBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.view.MenuItem;

import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.viewModel.index.OriginViewModel;


public class OriginActivity extends AppCompatActivity {

    private ActivityOriginBinding binding;

    private OriginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_origin);
        binding.setLifecycleOwner(this);

        vm = new ViewModelProvider(this).get(OriginViewModel.class);

        binding.setFragmentAdapter(new FragmentAdapter(this));
        binding.setViewModel(vm);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        binding.viewPager.setOffscreenPageLimit(2);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);

    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OriginActivity.class);
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