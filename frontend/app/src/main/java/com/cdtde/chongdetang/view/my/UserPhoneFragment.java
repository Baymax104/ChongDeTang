package com.cdtde.chongdetang.view.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentUserPhoneBinding;
import com.cdtde.chongdetang.viewModel.my.UserPhoneViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 19:49
 * @Version 1
 */
public class UserPhoneFragment extends Fragment {

    private FragmentUserPhoneBinding binding;

    private UserPhoneViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPhoneBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(UserPhoneViewModel.class);
        binding.setViewModel(vm);
    }

    public static UserPhoneFragment newInstance() {
        return new UserPhoneFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
