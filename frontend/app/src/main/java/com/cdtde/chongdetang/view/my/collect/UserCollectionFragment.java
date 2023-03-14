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
import com.cdtde.chongdetang.adapter.ExhibitCollectionAdapter;
import com.cdtde.chongdetang.databinding.FragmentUserCollectionBinding;
import com.cdtde.chongdetang.entity.UserCollect;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.view.exhibit.CollectionActivity;
import com.cdtde.chongdetang.view.shop.ItemCollectDialog;
import com.cdtde.chongdetang.viewModel.my.UserCollectViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/24 20:35
 * @Version 1
 */
public class UserCollectionFragment extends Fragment {

    private FragmentUserCollectionBinding binding;

    private UserCollectViewModel vm;

    private XPopup.Builder attachBuilder;

    private ItemCollectDialog dialog;

    public static UserCollectionFragment newInstance() {
        return new UserCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserCollectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        vm = new ViewModelProvider(requireActivity()).get(UserCollectViewModel.class);
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

        vm.updateUserCollection();

        LiveEventBus.get("MyRepository-requestUserCollection", WebException.class)
                .observe(this, exception -> {
                    if (exception.isSuccess()) {
                        vm.refreshUserCollection();
                    } else {
                        ToastUtils.showShort(exception.getMessage());
                    }
                });
        LiveEventBus.get("ItemCollectDialog-cancelCollect", UserCollect.class)
                .observe(this, userCollect -> {
                    if (vm.getCurrentPage() == 1) {
                        vm.removeUserCollect(userCollect);
                    }
                });

        LiveEventBus.get("MyRepository-requestRemoveUserCollect", WebException.class)
                .observe(this, e -> {
                    if (vm.getCurrentPage() == 1) {
                        dialog.smartDismiss();
                        if (e.isSuccess()) {
                            vm.updateUserCollection();
                        } else {
                            ToastUtils.showShort(e.getMessage());
                        }
                    }
                });
    }
}
