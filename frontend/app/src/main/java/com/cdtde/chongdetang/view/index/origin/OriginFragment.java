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
public class OriginFragment extends Fragment {

    private OriginViewModel vm;
    private FragmentOriginBinding binding;

    public static OriginFragment newInstance(int index) {
        OriginFragment fragment = new OriginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOriginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt("page");
        }
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(OriginViewModel.class);
        WebViewUtil.configure(binding.webPage, true);

        binding.setViewModel(vm);
        binding.setPage(index);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}