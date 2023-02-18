package com.cdtde.chongdetang.view.index.culture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCultureDetailBinding;
import com.cdtde.chongdetang.entity.Culture;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.index.CultureDetailViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

import io.reactivex.annotations.NonNull;

public class CultureDetailActivity extends AppCompatActivity {
    private ActivityCultureDetailBinding binding;
    private CultureDetailViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_culture_detail);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(CultureDetailViewModel.class);

        binding.setViewModel(vm);

        WindowUtil.initActivityWindow(binding.toolbar, this, true);
        WebViewUtil.configure(binding.webPage, false);

        LiveEventBus.get("CultureListFragment-onItemClick", Culture.class)
                .observeSticky(this, vm::setCulture);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CultureDetailActivity.class);
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