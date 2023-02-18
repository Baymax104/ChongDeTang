package com.cdtde.chongdetang.view.index.couplet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCoupletBinding;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.CoupletAdapter;
import com.cdtde.chongdetang.viewModel.index.CoupletViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class CoupletActivity extends AppCompatActivity {

    private ActivityCoupletBinding binding;

    private CoupletViewModel vm;

    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_couplet);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true, false);

        Glide.with(this)
                .asBitmap()
                .load(R.drawable.couplet_banner)
                .placeholder(R.drawable.loading)
                .into(binding.banner);

        vm = new ViewModelProvider(this).get(CoupletViewModel.class);
        binding.setViewModel(vm);

        CoupletAdapter adapter = new CoupletAdapter();
        adapter.setOnItemClickListener(data -> {
            CoupletDetailActivity.actionStart(this);
            LiveEventBus.get("CoupletActivity-onItemClick", News.class).post(data);
        });
        binding.setAdapter(adapter);

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false)).show();

        LiveEventBus.get("IndexRepository-getNews-mryl", Boolean.class)
                .observe(this, aBoolean -> {
                    loading.smartDismiss();
                    if (aBoolean) {
                        vm.refreshAllCouplet();
                    }
                });
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
        Intent intent = new Intent(context, CoupletActivity.class);
        context.startActivity(intent);
    }
}