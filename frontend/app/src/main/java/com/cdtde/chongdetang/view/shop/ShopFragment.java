package com.cdtde.chongdetang.view.shop;

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
import com.cdtde.chongdetang.adapter.ShopProductAdapter;
import com.cdtde.chongdetang.databinding.FragmentShopBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.index.SearchActivity;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.viewModel.MainViewModel;
import com.cdtde.chongdetang.viewModel.shop.ShopViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.youth.banner.indicator.CircleIndicator;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:03
 * @Version 1
 */
public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;
    private ShopViewModel vm;
    private MainViewModel mainViewModel;
    private XPopup.Builder attachBuilder;
    private ItemCollectDialog collectDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        binding.toolbar.inflateMenu(R.menu.index_toolbar);
        WindowUtil.initFragmentWindow(binding.toolbarLayout, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        vm = new ViewModelProvider(this).get(ShopViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(vm);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        attachBuilder = new XPopup.Builder(requireContext()).hasShadowBg(false);

        ShopProductAdapter adapter = new ShopProductAdapter();
        adapter.setOnItemClickListener(this::clickItem);
        adapter.setOnMoreClickListener((v, product) -> {
            attachBuilder.atView(v);
            collectDialog = (ItemCollectDialog) DialogUtil.create(requireContext(), ItemCollectDialog.class, attachBuilder);
            collectDialog.show();
            LiveEventBus.get("ItemCollectDialog-show", UserCollect.class)
                    .post(new UserCollect(product));
        });
        binding.setProductAdapter(adapter);

        mainViewModel.getPage().observe(this, page -> {
            if (page == 2 && !vm.isInit()) {
                vm.updateAllProduct();
            }
        });

        LiveEventBus.get("ShopRepository-requestAllProduct", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        vm.refreshAllProduct();
                        vm.setInit(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });
        LiveEventBus.get("User-isLogin", Boolean.class)
                .observeSticky(this, aBoolean -> {
                    Integer page;
                    if ((page = mainViewModel.getPage().getValue()) != null) {
                        if (page == 2) {
                            vm.updateAllProduct();
                        } else {
                            vm.setInit(false);
                        }
                    }
                });
        LiveEventBus.get("ItemCollectDialog-collect", UserCollect.class)
                .observe(this, userCollect -> {
                    Integer page = mainViewModel.getPage().getValue();
                    if (page != null && page == 2) {
                        if (vm.isLogin()) {
                            vm.addUserCollect(userCollect);
                        } else {
                            collectDialog.dismissWith(() ->
                                    LoginActivity.actionStart(requireContext())
                            );
                        }
                    }
                });
        LiveEventBus.get("ItemCollectDialog-cancelCollect", UserCollect.class)
                .observe(this, userCollect -> {
                    Integer page = mainViewModel.getPage().getValue();
                    if (page != null && page == 2) {
                        vm.removeUserCollect(userCollect);
                    }
                });

        LiveEventBus.get("ShopRepository-requestAddUserCollect", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        LiveEventBus.get("ItemCollectDialog-refreshAddCollect", Boolean.class).post(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });
        LiveEventBus.get("ShopRepository-requestRemoveUserCollect", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        LiveEventBus.get("ItemCollectDialog-refreshRemoveCollect", Boolean.class).post(true);
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });

        binding.banner.setIndicator(new CircleIndicator(getContext()));

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        });

        binding.hotProduct1.setClickHandler(this::clickItem);
        binding.hotProduct2.setClickHandler(this::clickItem);
        binding.hotProduct3.setClickHandler(this::clickItem);

        binding.shoppingEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                ShoppingActivity.actionStart(requireContext());
            } else {
                LoginActivity.actionStart(requireContext());
            }
        });
    }

    public void clickItem(Product product) {
        ProductActivity.actionStart(getContext(), product);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
