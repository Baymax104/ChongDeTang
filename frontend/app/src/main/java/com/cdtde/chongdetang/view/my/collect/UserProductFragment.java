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
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.view.shop.ItemCollectDialog;
import com.cdtde.chongdetang.view.shop.ProductActivity;
import com.cdtde.chongdetang.viewModel.my.UserCollectViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;

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

    private XPopup.Builder attachBuilder;

    private ItemCollectDialog dialog;

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
        binding.setLifecycleOwner(getViewLifecycleOwner());
        vm = new ViewModelProvider(requireActivity()).get(UserCollectViewModel.class);

        binding.setViewModel(vm);

        attachBuilder = new XPopup.Builder(requireContext()).hasShadowBg(false);

        ShopProductAdapter adapter = new ShopProductAdapter();
        adapter.setOnItemClickListener(data -> ProductActivity.actionStart(getContext(), data));
        adapter.setOnMoreClickListener((v, product) -> {
            attachBuilder.atView(v);
            dialog = (ItemCollectDialog) DialogUtil.create(requireContext(), ItemCollectDialog.class, attachBuilder);
            dialog.show();
            LiveEventBus.get("ItemCollectDialog-show", UserCollect.class)
                    .post(new UserCollect(product));
        });
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

        LiveEventBus.get("ItemCollectDialog-cancelCollect", UserCollect.class)
                .observe(this, userCollect -> {
                    if (vm.getCurrentPage() == 2) {
                        vm.removeUserCollect(userCollect);
                    }
                });

        LiveEventBus.get("MyRepository-requestRemoveUserCollect", WebException.class)
                .observe(this, e -> {
                    if (vm.getCurrentPage() == 2) {
                        dialog.smartDismiss();
                        if (e.isSuccess()) {
                            vm.updateUserProduct();
                        } else {
                            ToastUtils.showShort(e.getMessage());
                        }
                    }
                });
    }
}
