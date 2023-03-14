package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.adapter.ExhibitCollectionAdapter;
import com.cdtde.chongdetang.databinding.FragmentExhibitListBinding;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.view.shop.ItemCollectDialog;
import com.cdtde.chongdetang.viewModel.MainViewModel;
import com.cdtde.chongdetang.viewModel.exhibit.ExhibitViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:57
 * @Version 1
 */
public class ExhibitListFragment extends Fragment {

    private FragmentExhibitListBinding binding;

    // TabFragment与ExhibitFragment的ViewModel是共享的，都属于MainActivity
    private ExhibitViewModel vm;

    private MainViewModel mainViewModel;

    private XPopup.Builder attachBuilder;

    private ItemCollectDialog dialog;

    private int page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExhibitListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        page = 1;
        if (getArguments() != null) {
            page = getArguments().getInt("page");
        }
        binding.setLifecycleOwner(getViewLifecycleOwner());
        vm = new ViewModelProvider(requireActivity()).get(ExhibitViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.setViewModel(vm);

        attachBuilder = new XPopup.Builder(requireContext()).hasShadowBg(false);

        ExhibitCollectionAdapter adapter = new ExhibitCollectionAdapter();
        adapter.setOnItemClickListener(data ->
            CollectionActivity.actionStart(requireContext(), data)
        );
        adapter.setOnMoreClickListener((v, collection) -> {
            attachBuilder.atView(v);
            dialog = (ItemCollectDialog) DialogUtil.create(requireContext(), ItemCollectDialog.class, attachBuilder);
            dialog.show();
            LiveEventBus.get("ItemCollectDialog-show", UserCollect.class)
                    .post(new UserCollect(collection));
        });
        binding.setAdapter(adapter);
        binding.setPage(page);

        // 由于ItemCollectDialog发送消息是全局发送，因此每个页面需要判断当前操作的页面是否是自己
        // 当前页面的ViewModel记录页面内的子页面页数状态，MainViewModel记录四个主页面的页数状态
        LiveEventBus.get("ItemCollectDialog-collect", UserCollect.class)
                .observe(this, userCollect -> {
                    Integer mainPage = mainViewModel.getPage().getValue();
                    if (mainPage != null && mainPage == 1 && page == vm.getCurrentPage()) {
                        if (vm.isLogin()) {
                            vm.addUserCollect(userCollect);
                        } else {
                            dialog.dismissWith(() ->
                                    LoginActivity.actionStart(requireContext())
                            );
                        }
                    }
                });
        LiveEventBus.get("ItemCollectDialog-cancelCollect", UserCollect.class)
                .observe(this, userCollect -> {
                    Integer mainPage = mainViewModel.getPage().getValue();
                    if (mainPage != null && mainPage == 1 && page == vm.getCurrentPage()) {
                        vm.removeUserCollect(userCollect);
                    }
                });

        LiveEventBus.get("ExhibitRepository-requestAddUserCollect", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        LiveEventBus.get("ItemCollectDialog-refreshAddCollect", Boolean.class).post(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });
        LiveEventBus.get("ExhibitRepository-requestRemoveUserCollect", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        LiveEventBus.get("ItemCollectDialog-refreshRemoveCollect", Boolean.class).post(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });
    }

    public static ExhibitListFragment newInstance(int page) {
        ExhibitListFragment fragment = new ExhibitListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
