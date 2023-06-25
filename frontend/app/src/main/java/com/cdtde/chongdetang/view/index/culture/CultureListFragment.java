package com.cdtde.chongdetang.view.index.culture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.adapter.recycler.CultureAdapter;
import com.cdtde.chongdetang.databinding.FragmentCultureListBinding;
import com.cdtde.chongdetang.viewModel.index.CultureViewModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class CultureListFragment extends Fragment {

    private CultureViewModel vm;
    private FragmentCultureListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCultureListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int page = 1;
        if (getArguments() != null) {
            page = getArguments().getInt("page");
        }
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(CultureViewModel.class);

        binding.setViewModel(vm);
        CultureAdapter cultureAdapter = new CultureAdapter();
//        cultureAdapter.setOnItemClickListener(data ->
//            CultureDetailActivity.actionStart(getContext(), data)
//        );
        binding.setAdapter(cultureAdapter);
        binding.setPage(page);
    }

    public static CultureListFragment newInstance(int index) {
        CultureListFragment fragment = new CultureListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}