package com.cdtde.chongdetang.view.my.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.adapter.ShopProductAdapter;
import com.cdtde.chongdetang.databinding.FragmentUserProductBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.view.shop.ProductActivity;
import com.cdtde.chongdetang.viewModel.my.UserCollectViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 20:40
 * @Version 1
 */
public class UserProductFragment extends Fragment {

    private FragmentUserProductBinding binding;

    private UserCollectViewModel vm;

    public static UserProductFragment newInstance() {
        return new UserProductFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(UserCollectViewModel.class);

        binding.setViewModel(vm);

        ShopProductAdapter adapter = new ShopProductAdapter();
        adapter.setOnItemClickListener(data -> ProductActivity.actionStart(getContext(), data));
        binding.setAdapter(adapter);

        vm.updateUserProduct();

        LiveEventBus.get("MyRepository-requestUserProduct", WebException.class)
                .observe(this, exception -> {
                    if (exception.isSuccess()) {
                        vm.refreshUserProduct();
                    } else {
                        ToastUtils.showShort(exception.getMessage());
                    }
                });
    }
}
