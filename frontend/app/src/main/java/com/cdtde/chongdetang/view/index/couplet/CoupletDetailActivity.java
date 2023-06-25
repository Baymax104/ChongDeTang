package com.cdtde.chongdetang.view.index.couplet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCoupletDetailBinding;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.viewModel.index.CoupletDetailViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class CoupletDetailActivity extends AppCompatActivity {

    private ActivityCoupletDetailBinding binding;

    private CoupletDetailViewModel vm;

    public static void actionStart(Context context, News couplet) {
        LiveEventBus.get("CoupletDetailActivity-getData", News.class).post(couplet);
        Intent intent = new Intent(context, CoupletDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_couplet_detail);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(CoupletDetailViewModel.class);
        binding.setViewModel(vm);

//        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);
        WebViewUtil.configure(binding.webPage, false);

        LiveEventBus.get("CoupletDetailActivity-getData", News.class)
                .observeSticky(this, vm::setCouplet);
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