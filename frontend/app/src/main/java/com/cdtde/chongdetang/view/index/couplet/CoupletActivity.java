package com.cdtde.chongdetang.view.index.couplet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.recycler.CoupletAdapter;
import com.cdtde.chongdetang.databinding.ActivityCoupletBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.utils.DialogUtil;
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
//        WindowUtil.initActivityWindow(binding.toolbar, this, true, false);

        Glide.with(this)
                .asBitmap()
                .load(R.drawable.couplet_banner)
                .placeholder(R.drawable.loading)
                .into(binding.banner);

        vm = new ViewModelProvider(this).get(CoupletViewModel.class);
//        binding.setViewModel(vm);

        CoupletAdapter adapter = new CoupletAdapter();
//        adapter.setOnItemClickListener(data ->
//                CoupletDetailActivity.actionStart(this, data)
//        );
//        binding.setAdapter(adapter);

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false)).show();

        LiveEventBus.get("IndexRepository-requestNews-mryl", WebException.class)
                .observe(this, e -> {
                    loading.smartDismiss();
                    if (e.isSuccess()) {
                        vm.refreshAllCouplet();
                    } else {
                        ToastUtils.showShort(e.getMessage());
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