package com.cdtde.chongdetang.view.index.culture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCultureBinding;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.viewModel.index.CultureViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;


public class CultureActivity extends AppCompatActivity {

    private ActivityCultureBinding binding;
    private CultureViewModel vm;

    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_culture);
        vm = new ViewModelProvider(this).get(CultureViewModel.class);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);
        binding.setLifecycleOwner(this);

        binding.setViewModel(vm);
        binding.setFragmentAdapter(new FragmentAdapter(this));

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false));

        binding.viewPager.setOffscreenPageLimit(2);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);

        LiveEventBus.get("IndexRepository-getAllCulture", Boolean.class)
                .observe(this, aBoolean -> {
                    loading.smartDismiss();
                    if (aBoolean) {
                        vm.refreshAllCulture();
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
        Intent intent = new Intent(context, CultureActivity.class);
        context.startActivity(intent);
    }

}