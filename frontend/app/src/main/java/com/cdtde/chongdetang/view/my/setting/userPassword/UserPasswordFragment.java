package com.cdtde.chongdetang.view.my.setting.userPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentUserPasswordBinding;
import com.cdtde.chongdetang.view.my.setting.userPhone.UserPhoneFragment;
import com.cdtde.chongdetang.viewModel.my.UserPasswordViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 0:09
 * @Version 1
 */
public class UserPasswordFragment extends Fragment {

    private FragmentUserPasswordBinding binding;

    private UserPasswordViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(UserPasswordViewModel.class);
        binding.setViewModel(vm);
    }

    public static UserPasswordFragment newInstance() {
        return new UserPasswordFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
