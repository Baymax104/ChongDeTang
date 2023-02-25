package com.cdtde.chongdetang.view.my.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentRegisterBinding;
import com.cdtde.chongdetang.viewModel.my.RegisterViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/5 2:20
 * @Version 1
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        binding.setViewModel(vm);

        binding.register.setOnClickListener(v -> {
            if (vm.validateRegister()) {
                vm.setPage(2);
            }
        });
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
