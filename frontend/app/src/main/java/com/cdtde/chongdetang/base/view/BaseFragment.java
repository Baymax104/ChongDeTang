package com.cdtde.chongdetang.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.cdtde.chongdetang.base.vm.FragmentViewModelScope;
import com.cdtde.chongdetang.base.vm.Injector;

/**
 * 该类为项目中的所有 Activity 父类，包含了通用的初始化流程，同时重写了一些原生通用方法.
 * @see BaseActivity
 * @param <B> Fragment 子类的 DataBinding 类型
 * @author John
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {

    protected AppCompatActivity activity;

    private ViewDataBinding mBinding;

    private FragmentViewModelScope viewModelScope;

    /**
     * @see BaseActivity#configBinding()
     */
    protected abstract ViewConfig configBinding();

    /**
     * 对于 Fragment ，需要设置的控件较少，子类可选择性重写.
     * @see BaseActivity#initUIComponent(ViewDataBinding)
     */
    protected void initUIComponent(@NonNull B binding) {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModelScope = new FragmentViewModelScope(this);
        Injector.inject(this, viewModelScope);

        ViewConfig config = configBinding();
        B binding = DataBindingUtil.inflate(inflater, config.layout, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        initUIComponent(binding);

        for (int i = 0; i < config.params.size(); i++) {
            int key = config.params.keyAt(i);
            Object value = config.params.get(key);
            binding.setVariable(key, value);
        }
        mBinding = binding;

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.unbind();
        mBinding = null;
    }
}
