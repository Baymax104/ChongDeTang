package com.cdtde.chongdetang.view.my.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentValidateBinding;
import com.cdtde.chongdetang.viewModel.my.ValidateViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/15 0:05
 * @Version 1
 */
public class ValidateFragment extends Fragment {

    private FragmentValidateBinding binding;

    private ValidateViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentValidateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String vmType = "";
        if (getArguments() != null) {
            vmType = getArguments().getString("class");
        }
        binding.setLifecycleOwner(this);

        Class<? extends ValidateViewModel> cl;
        try {
            cl = (Class<? extends ValidateViewModel>) Class.forName(vmType);
            vm = new ViewModelProvider(requireActivity()).get(cl);
            binding.setViewModel(vm);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ValidateFragment newInstance(String vmType) {
        ValidateFragment fragment = new ValidateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("class", vmType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
