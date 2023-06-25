package com.cdtde.chongdetang.view.index.culture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.view.BaseActivity;
import com.cdtde.chongdetang.base.view.ViewConfig;
import com.cdtde.chongdetang.base.vm.State;
import com.cdtde.chongdetang.base.vm.StateHolder;
import com.cdtde.chongdetang.databinding.ActivityCultureDetailBinding;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.utils.WebViewUtil;
import com.cdtde.chongdetang.utils.WindowUtil;
import com.cdtde.chongdetang.viewModel.index.CultureDetailViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class CultureDetailActivity extends BaseActivity<ActivityCultureDetailBinding> {
    private ActivityCultureDetailBinding binding;
    private CultureDetailViewModel vm;


    public static class States extends StateHolder {
        public final State<Culture> culture = new State<>(new Culture());
    }

    @Override
    protected ViewConfig configBinding() {
        return null;
    }

    @Override
    protected void initUIComponent(@androidx.annotation.NonNull ActivityCultureDetailBinding binding) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_culture_detail);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(CultureDetailViewModel.class);

        binding.setViewModel(vm);

        WindowUtil.initActivityWindow(this, binding.toolbar, binding.toolbar);
        WebViewUtil.configure(binding.webPage, false);

        LiveEventBus.get("CultureDetailActivity-getData", Culture.class)
                .observeSticky(this, vm::setCulture);
    }

    public static void actionStart(Context context, Culture culture) {
        LiveEventBus.get("CultureDetailActivity-getData", Culture.class).post(culture);
        Intent intent = new Intent(context, CultureDetailActivity.class);
        context.startActivity(intent);
    }

}