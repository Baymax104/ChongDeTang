package com.cdtde.chongdetang.view.index.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityInfoDetailBinding;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.index.moment.MomentActivity;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class InfoDetailActivity extends AppCompatActivity {

    private ActivityInfoDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info_detail);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        WebViewUtil.configure(binding.webPage, false);

        LiveEventBus.get("InfoActivity-onItemClick", News.class)
                .observeSticky(this, binding::setInfo);
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
        Intent intent = new Intent(context, InfoDetailActivity.class);
        context.startActivity(intent);
    }

}