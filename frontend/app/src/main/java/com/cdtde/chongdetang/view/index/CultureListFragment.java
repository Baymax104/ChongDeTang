package com.cdtde.chongdetang.view.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentCultureListBinding;
import com.cdtde.chongdetang.util.adapter.CultureAdapter;
import com.cdtde.chongdetang.viewModel.CultureViewModel;



/**
 * A placeholder fragment containing a simple view.
 */
public class CultureListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

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
            page = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        vm = new ViewModelProvider(this).get(CultureViewModel.class);
        vm.setIndex(page);

        binding.setPage(page);
        CultureAdapter cultureAdapter = new CultureAdapter();
        int finalPage = page;
        cultureAdapter.setOnItemClickListener(data -> {
            String str = data.getUrl();
            vm.setDetailUel(str);
            CultureDeatailActivity.actionStart(getContext());
        });
        binding.setAdapter(cultureAdapter);
        binding.setViewModel(vm);
    }

    public static CultureListFragment newInstance(int index) {
        CultureListFragment fragment = new CultureListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}