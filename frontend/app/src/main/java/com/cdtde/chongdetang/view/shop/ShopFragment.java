package com.cdtde.chongdetang.view.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentShopBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.ShopProductAdapter;
import com.cdtde.chongdetang.view.SearchActivity;
import com.cdtde.chongdetang.viewModel.shop.ShopViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
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
        binding.setLifecycleOwner(this);
        binding.setViewModel(vm);

        ShopProductAdapter adapter = new ShopProductAdapter();
        adapter.setOnItemClickListener(this::clickItem);
        binding.setProductAdapter(adapter);

        LiveEventBus.get("MainActivity-page", Integer.class)
                        .observeSticky(this, page -> {
                            if (page == 2 && !vm.isInit()) {
                                vm.updateAllProduct();
                            }
                        });

        LiveEventBus.get("ShopRepository-getAllProduct", Boolean.class)
                        .observe(this, aBoolean -> {
                            if (aBoolean) {
                                vm.refreshAllProduct();
                                vm.setInit(true);
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
    }

    public void clickItem(Product product) {
        ProductActivity.actionStart(getContext());
        LiveEventBus.get("ShopFragment-onItemClick", Product.class).post(product);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
