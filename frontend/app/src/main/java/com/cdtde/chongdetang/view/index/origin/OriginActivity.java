package com.cdtde.chongdetang.view.index.origin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdtde.chongdetang.databinding.ActivityOriginBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.OriginPagerAdapter;


public class OriginActivity extends AppCompatActivity {

    private ActivityOriginBinding binding;
    //vm在 Fragment中声明

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOriginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        OriginPagerAdapter originPagerAdapter = new OriginPagerAdapter(this, getSupportFragmentManager());
        binding.viewPager.setAdapter(originPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.setOffscreenPageLimit(2);

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