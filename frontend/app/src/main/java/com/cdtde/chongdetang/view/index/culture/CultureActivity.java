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
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.databinding.ActivityCultureBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.utils.DialogUtil;
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
//        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);
        binding.setLifecycleOwner(this);

//        binding.setViewModel(vm);
        binding.setFragmentAdapter(new FragmentAdapter(this));

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false)).show();

        binding.viewPager.setOffscreenPageLimit(2);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);

        LiveEventBus.get("IndexRepository-requestAllCulture", WebException.class)
                .observe(this, e -> {
                    loading.smartDismiss();
                    if (e.isSuccess()) {
                        vm.refreshAllCulture();
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
        Intent intent = new Intent(context, CultureActivity.class);
        context.startActivity(intent);
    }

}