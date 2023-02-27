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
import com.cdtde.chongdetang.dataSource.web.WebException;
import com.cdtde.chongdetang.databinding.FragmentUserCollectionBinding;
import com.cdtde.chongdetang.util.adapter.ExhibitCollectionAdapter;
import com.cdtde.chongdetang.view.exhibit.CollectionActivity;
import com.cdtde.chongdetang.viewModel.my.UserCollectViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

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
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(UserCollectViewModel.class);
        binding.setViewModel(vm);

        ExhibitCollectionAdapter adapter = new ExhibitCollectionAdapter();
        adapter.setOnItemClickListener(data ->
                CollectionActivity.actionStart(requireContext(), data)
        );
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
    }
}
