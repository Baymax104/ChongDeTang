package com.cdtde.chongdetang.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.databinding.ActivityMainBinding;
import com.cdtde.chongdetang.view.exhibit.ExhibitFragment;
import com.cdtde.chongdetang.view.index.IndexFragment;
import com.cdtde.chongdetang.view.my.MyFragment;
import com.cdtde.chongdetang.view.shop.ShopFragment;
import com.cdtde.chongdetang.viewModel.MainViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(vm);
        binding.setFragmentAdapter(new FragmentAdapter(this));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        vm.getPage().observe(this, page -> {
            binding.viewPager.setCurrentItem(page, false);
            binding.mainNav.getMenu().getItem(page).setChecked(true);
        });

        binding.mainNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_index) {
                vm.setPage(0);
            } else if (id == R.id.nav_exhibit) {
                vm.setPage(1);
            } else if (id == R.id.nav_shop) {
                vm.setPage(2);
            } else if (id == R.id.nav_my) {
                vm.setPage(3);
            }
            return true;
        });

        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setOffscreenPageLimit(3);
    }

}