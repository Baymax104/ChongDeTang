package com.cdtde.chongdetang.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityMainBinding;
import com.cdtde.chongdetang.util.FragmentAdapter;
import com.cdtde.chongdetang.view.my.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();

        binding.mainNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_index) {
                binding.viewPager.setCurrentItem(0);
            } else if (id == R.id.nav_exhibit) {
                binding.viewPager.setCurrentItem(1);
            } else if (id == R.id.nav_shop) {
                binding.viewPager.setCurrentItem(2);
            } else if (id == R.id.nav_my) {
                binding.viewPager.setCurrentItem(3);
            }
            return true;
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.mainNav.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new ExhibitFragment());
        fragments.add(new ShopFragment());
        fragments.add(new MyFragment());
        FragmentAdapter adapter = new FragmentAdapter(this, fragments);
        binding.viewPager.setAdapter(adapter);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

}