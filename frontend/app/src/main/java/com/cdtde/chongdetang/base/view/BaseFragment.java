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
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/16 16:57
 * @Version 1
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {

    protected AppCompatActivity activity;

    private ViewDataBinding mBinding;

    private FragmentViewModelScope viewModelScope;

    protected abstract ViewConfig configBinding();

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
