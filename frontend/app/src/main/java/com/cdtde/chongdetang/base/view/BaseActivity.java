package com.cdtde.chongdetang.base.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.base.vm.ActivityViewModelScope;
import com.cdtde.chongdetang.base.vm.Injector;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/8 18:45
 * @Version 1
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    private ViewDataBinding mBinding;

    private ActivityViewModelScope viewModelScope;

    protected AppCompatActivity activity;

    protected abstract ViewConfig configBinding();

    protected abstract void initUIComponent(@NonNull B binding);

    protected Integer createToolbarMenu() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        viewModelScope = new ActivityViewModelScope(this);
        Injector.inject(this, viewModelScope);

        ViewConfig config = configBinding();
        B binding = DataBindingUtil.setContentView(this, config.layout);
        binding.setLifecycleOwner(this);
        applyToolbar(binding.getRoot());
        initUIComponent(binding);

        for (int i = 0; i < config.params.size(); i++) {
            int key = config.params.keyAt(i);
            Object value = config.params.get(key);
            binding.setVariable(key, value);
        }
        mBinding = binding;
    }

    private void applyToolbar(View root) {
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Integer menuId = createToolbarMenu();
        if (menuId != null) {
            getMenuInflater().inflate(menuId, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.unbind();
        mBinding = null;
    }
}
