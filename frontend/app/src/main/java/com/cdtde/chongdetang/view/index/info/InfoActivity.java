package com.cdtde.chongdetang.view.index.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.NewsAdapter;
import com.cdtde.chongdetang.databinding.ActivityInfoBinding;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.util.WindowUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Arrays;

public class InfoActivity extends AppCompatActivity {

    private ActivityInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info);
        binding.setLifecycleOwner(this);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        LiveEventBus.get("IndexFragment-allInfo", News[].class)
                .observeSticky(this, news -> binding.setInfos(Arrays.asList(news)));

        NewsAdapter adapter = new NewsAdapter();
        adapter.setOnItemClickListener(data ->
            InfoDetailActivity.actionStart(this, data)
        );
        binding.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, InfoActivity.class);
        context.startActivity(intent);
    }

}