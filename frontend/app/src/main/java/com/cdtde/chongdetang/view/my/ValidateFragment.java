package com.cdtde.chongdetang.view.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentValidateBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.UserPasswordViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 0:05
 * @Version 1
 */
public class ValidateFragment extends Fragment {

    private FragmentValidateBinding binding;

    private UserPasswordViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentValidateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(UserPasswordViewModel.class);
        binding.setUser(vm.getUser());
        binding.setViewModel(vm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
