package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.angcyo.tablayout.delegate2.ViewPager2Delegate;
import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.databinding.FragmentExhibitBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.index.SearchActivity;
import com.cdtde.chongdetang.viewModel.MainViewModel;
import com.cdtde.chongdetang.viewModel.exhibit.ExhibitViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:02
 * @Version 1
 */
public class ExhibitFragment extends Fragment {

    private FragmentExhibitBinding binding;

    private ExhibitViewModel vm;

    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExhibitBinding.inflate(inflater, container, false);
        binding.toolbar.inflateMenu(R.menu.index_toolbar);
        WindowUtil.initFragmentWindow(binding.toolbarLayout, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        vm = new ViewModelProvider(requireActivity()).get(ExhibitViewModel.class);
        binding.setLifecycleOwner(this);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.setViewModel(vm);
        binding.setTabAdapter(new FragmentAdapter(requireActivity()));

        mainViewModel.getPage().observe(this, page -> {
            if (page == 1 && !vm.isInit()) {
                vm.updateAllCollection();
            }
        });

        LiveEventBus.get("ExhibitRepository-requestAllCollection", WebException.class)
                        .observe(this, exception -> {
                            if (exception.isSuccess()) {
                                vm.refreshAllCollection();
                                vm.setInit(true);
                            } else {
                                ToastUtils.showShort(exception.getMessage());
                            }
                        });

        binding.viewPager.setOffscreenPageLimit(2);
        ViewPager2Delegate.Companion.install(binding.viewPager, binding.tabs, true);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
