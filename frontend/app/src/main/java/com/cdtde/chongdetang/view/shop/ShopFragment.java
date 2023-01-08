package com.cdtde.chongdetang.view.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentShopBinding;
import com.cdtde.chongdetang.util.adapter.ShopProductAdapter;
import com.cdtde.chongdetang.view.SearchActivity;
import com.cdtde.chongdetang.viewModel.ShopViewModel;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;

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
        binding.toolbar.inflateMenu(R.menu.index_toolbar_menu);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        binding.toolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        vm = new ViewModelProvider(this).get(ShopViewModel.class);
        binding.setLifecycleOwner(this);

        binding.setViewModel(vm);
        binding.setProductAdapter(new ShopProductAdapter());

        binding.banner.setIndicator(new CircleIndicator(getContext()));

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
