package com.cdtde.chongdetang.view.index.moment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityMomentBinding;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.NewsAdapter;
import com.cdtde.chongdetang.view.index.couplet.CoupletActivity;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MomentActivity extends AppCompatActivity {

    private ActivityMomentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moment);
        binding.setLifecycleOwner(this);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        LiveEventBus.get("IndexFragment-allMoment", News[].class)
                .observeSticky(this, news -> binding.setMoments(Arrays.asList(news)));

        NewsAdapter adapter = new NewsAdapter();
        adapter.setOnItemClickListener(data -> {
            LiveEventBus.get("MomentActivity-onItemClick", News.class).post(data);
            MomentDetailActivity.actionStart(this);
        });
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
        Intent intent = new Intent(context, MomentActivity.class);
        context.startActivity(intent);
    }
}