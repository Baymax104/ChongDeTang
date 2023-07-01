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
 * 该类为项目中的所有 Activity 父类，包含了通用的初始化流程，同时重写了一些原生通用方法.
 * 初始化流程如下
 * <ul>
 *     <li>创建子类 ViewModel 作用域对象 {@link ActivityViewModelScope} </li>
 *     <li>通过 {@link Injector} 注入子类中的 ViewModel 组件依赖</li>
 *     <li>使用 {@link BaseActivity#configBinding()} 方法配置的布局ID创建视图的 ViewDataBinding 对象并进行设置</li>
 *     <li>若布局中包含自定义 Toolbar ，则替换默认 Actionbar</li>
 *     <li>将 ViewDataBinding 对象传入 {@link BaseActivity#initUIComponent(ViewDataBinding)}中，由子类设置UI控件</li>
 *     <li>遍历配置的绑定参数，将其绑定到视图中</li>
 * </ul>
 * @param <B> 子类 DataBinding 类型
 * @author John
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    private ViewDataBinding mBinding;

    private ActivityViewModelScope viewModelScope;

    protected AppCompatActivity activity;

    /**
     * 该方法必须由子类重写，返回子类绑定到视图中的参数.
     * @return ViewConfig 封装了子类需要绑定到视图中的参数
     */
    protected abstract ViewConfig configBinding();

    /**
     * 该方法必须由子类重写，子类可以使用 binding 参数获取视图中的UI控件进行设置.
     * @param binding 子类 DataBinding 对象，由 BaseActivity 提供
     */
    protected abstract void initUIComponent(@NonNull B binding);

    /**
     * 当子类需要配置Toolbar菜单时，重写该方法.
     * @return Toolbar菜单ID
     */
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

    /**
     * @hide
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Integer menuId = createToolbarMenu();
        if (menuId != null) {
            getMenuInflater().inflate(menuId, menu);
            return true;
        }
        return false;
    }

    /**
     * @hide
     */
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
