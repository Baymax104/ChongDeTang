package com.cdtde.chongdetang.view.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.dataSource.web.WebException;
import com.cdtde.chongdetang.databinding.FragmentIndexBinding;
import com.cdtde.chongdetang.entity.Collection;
import com.cdtde.chongdetang.entity.News;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.IndexCollectionAdapter;
import com.cdtde.chongdetang.view.exhibit.CollectionActivity;
import com.cdtde.chongdetang.view.index.appoint.AppointActivity;
import com.cdtde.chongdetang.view.index.couplet.CoupletActivity;
import com.cdtde.chongdetang.view.index.culture.CultureActivity;
import com.cdtde.chongdetang.view.index.info.InfoActivity;
import com.cdtde.chongdetang.view.index.info.InfoDetailActivity;
import com.cdtde.chongdetang.view.index.moment.MomentActivity;
import com.cdtde.chongdetang.view.index.moment.MomentDetailActivity;
import com.cdtde.chongdetang.view.index.origin.OriginActivity;
import com.cdtde.chongdetang.view.index.scenes.ScenesActivity;
import com.cdtde.chongdetang.viewModel.index.IndexViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
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
        binding.setViewModel(vm);

        IndexCollectionAdapter adapter = new IndexCollectionAdapter();
        adapter.setOnItemClickListener(data -> {
            LiveEventBus.get("TabFragment-onItemClick", Collection.class).post(data);
            CollectionActivity.actionStart(requireContext());
        });
        binding.setCollectionAdapter(adapter);

        binding.banner.setIndicator(new CircleIndicator(getContext()));

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        });

        LiveEventBus.get("MainActivity-page", Integer.class)
                .observeSticky(this, page -> {
                    if (page == 0) {
                        if (!vm.isMomentInit()) {
                            vm.updateAllMoment();
                        }
                        if (!vm.isInfoInit()) {
                            vm.updateAllInfo();
                        }
                        if (!vm.isHotCollectionInit()) {
                            vm.updateHotCollection();
                        }
                    }
                });

        LiveEventBus.get("IndexRepository-requestNews-zgdt", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        vm.refreshAllMoment();
                        vm.setMomentInit(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });

        LiveEventBus.get("IndexRepository-requestNews-hyzx", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        vm.refreshAllInfo();
                        vm.setInfoInit(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });

        LiveEventBus.get("IndexRepository-requestHotCollection", WebException.class)
                        .observe(this, exception -> {
                            if (exception.isSuccess()) {
                                vm.refreshHotCollection();
                                vm.setHotCollectionInit(true);
                            } else {
                                ToastUtils.showShort(exception.getMessage());
                            }
                        });

        binding.entry1.getRoot().setOnClickListener(v -> ScenesActivity.actionStart(getContext()));

        binding.entry2.getRoot().setOnClickListener(v -> AppointActivity.actionStart(getContext()));

        binding.entry3.getRoot().setOnClickListener(v -> CoupletActivity.actionStart(getContext()));

        binding.entry4.getRoot().setOnClickListener(v -> CultureActivity.actionStart(getContext()));

        binding.entry5.getRoot().setOnClickListener(v -> OriginActivity.actionStart(getContext()));

        binding.entry6.getRoot().setOnClickListener(v -> ContactActivity.actionStart(getContext()));

        binding.firstMoment.getRoot().setOnClickListener(v -> {
            News news = binding.firstInfo.getNews();
            if (news != null) {
                LiveEventBus.get("MomentActivity-onItemClick", News.class).post(news);
                MomentDetailActivity.actionStart(getContext());
            }
        });
        binding.momentLabel.allEntry.setOnClickListener(v -> MomentActivity.actionStart(getContext()));

        binding.firstInfo.getRoot().setOnClickListener(v -> {
            News news = binding.firstInfo.getNews();
            if (news != null) {
                LiveEventBus.get("InfoActivity-onItemClick", News.class).post(news);
                InfoDetailActivity.actionStart(getContext());
            }
        });
        binding.infoLabel.allEntry.setOnClickListener(v -> InfoActivity.actionStart(getContext()));

        binding.collectionLabel.allEntry.setOnClickListener(v ->
            LiveEventBus.get("IndexFragment-allCollection", Boolean.class).post(true)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
