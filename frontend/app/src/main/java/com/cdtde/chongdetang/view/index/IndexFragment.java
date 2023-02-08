package com.cdtde.chongdetang.view.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentIndexBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.IndexCollectionAdapter;
import com.cdtde.chongdetang.view.SearchActivity;
import com.cdtde.chongdetang.view.index.appoint.AppointActivity;
import com.cdtde.chongdetang.view.index.origin.OriginActivity;
import com.cdtde.chongdetang.viewModel.index.IndexViewModel;
import com.youth.banner.indicator.CircleIndicator;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 20:52
 * @Version 1
 */
public class IndexFragment extends Fragment {
    private FragmentIndexBinding binding;

    private IndexViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        // 初始化ToolBar
        binding.toolbar.inflateMenu(R.menu.index_toolbar);
        WindowUtil.initFragmentWindow(binding.toolbarLayout, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        vm = new ViewModelProvider(requireActivity()).get(IndexViewModel.class);
        binding.setLifecycleOwner(this);

        binding.setCollectionAdapter(new IndexCollectionAdapter());
        binding.setViewModel(vm);

        binding.banner.setIndicator(new CircleIndicator(getContext()));

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        });

        binding.entry1.getRoot().setOnClickListener(v -> ScenesActivity.actionStart(getContext()));

        binding.entry2.getRoot().setOnClickListener(v -> AppointActivity.actionStart(getContext()));

        binding.entry5.getRoot().setOnClickListener(v -> OriginActivity.actionStart(getContext()));

        binding.entry6.getRoot().setOnClickListener(v -> ContactActivity.actionStart(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
