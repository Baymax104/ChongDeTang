package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cdtde.chongdetang.databinding.FragmentExhibitListBinding;
import com.cdtde.chongdetang.model.Collection;
import com.cdtde.chongdetang.util.ExhibitCollectionAdapter;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 22:57
 * @Version 1
 */
public class TabFragment extends Fragment {

    private FragmentExhibitListBinding binding;

    private List<Collection> data;

    public TabFragment(List<Collection> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExhibitListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ExhibitCollectionAdapter adapter = new ExhibitCollectionAdapter(data);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.exhibitTabList.setLayoutManager(manager);
        binding.exhibitTabList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
