package com.cdtde.chongdetang.view.index.origin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentOriginBinding;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.viewModel.index.OriginViewModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class OriginWebFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private OriginViewModel vm;
    private FragmentOriginBinding binding;

    public static OriginWebFragment newInstance(int index) {
        OriginWebFragment fragment = new OriginWebFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        binding = FragmentOriginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        View root = binding.getRoot();

        WebViewUtil.configure(binding.originWebPage, true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(OriginViewModel.class);
        binding.setViewModel(vm);
        int index = 0;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        vm.setIndex(index);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}