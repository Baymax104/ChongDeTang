package com.cdtde.chongdetang.view.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.databinding.FragmentOriginBinding;
import com.cdtde.chongdetang.util.WebViewUtil;
import com.cdtde.chongdetang.viewModel.OriginViewModel;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(OriginViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        vm.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        binding = FragmentOriginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        View root = binding.getRoot();


        String webUrl = vm.getUrl();   //获取视图的网页地址
        WebViewUtil.configure(binding.originWebPage, true);
        binding.originWebPage.loadUrl(webUrl);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}