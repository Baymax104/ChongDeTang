package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.adapter.ExhibitCollectionAdapter;
import com.cdtde.chongdetang.databinding.FragmentExhibitListBinding;
import com.cdtde.chongdetang.viewModel.exhibit.ExhibitViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:57
 * @Version 1
 */
public class TabFragment extends Fragment {

    private FragmentExhibitListBinding binding;

    // TabFragment与ExhibitFragment的ViewModel是共享的，都属于MainActivity
    private ExhibitViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExhibitListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int page = 1;
        if (getArguments() != null) {
            page = getArguments().getInt("page");
        }
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(ExhibitViewModel.class);
        binding.setViewModel(vm);

        ExhibitCollectionAdapter adapter = new ExhibitCollectionAdapter();
        adapter.setOnItemClickListener(data ->
            CollectionActivity.actionStart(requireContext(), data)
        );
        binding.setAdapter(adapter);
        binding.setPage(page);
    }

    public static TabFragment newInstance(int page) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page", page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
