package com.cdtde.chongdetang.view.exhibit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.exhibit.CollectionViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class CollectionActivity extends AppCompatActivity {

    private ActivityCollectionBinding binding;

    private CollectionViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        vm = new ViewModelProvider(this).get(CollectionViewModel.class);
        binding.setViewModel(vm);

        WebViewUtil.configure(binding.webPage, false);

        LiveEventBus.get("TabFragment-onItemClick", Collection.class)
                .observeSticky(this, vm::setCollection);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, CollectionActivity.class));
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