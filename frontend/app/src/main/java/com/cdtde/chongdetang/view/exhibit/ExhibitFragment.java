package com.cdtde.chongdetang.view.exhibit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentExhibitBinding;
import com.cdtde.chongdetang.model.Collection;
import com.cdtde.chongdetang.util.FragmentAdapter;
import com.cdtde.chongdetang.view.SearchActivity;
import com.cdtde.chongdetang.viewModel.ExhibitViewModel;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:02
 * @Version 1
 */
public class ExhibitFragment extends Fragment {

    private FragmentExhibitBinding binding;

    private ExhibitViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExhibitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        initView();
        vm = new ViewModelProvider(requireActivity()).get(ExhibitViewModel.class);


        binding.exhibitTabs.setTabData(vm.getTabs());

        FragmentAdapter adapter = new FragmentAdapter(requireActivity(), vm.getTabFragments());
        binding.exhibitViewPager.setAdapter(adapter);

        binding.exhibitViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.exhibitTabs.setCurrentTab(position);
            }
        });

        binding.exhibitTabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                binding.exhibitViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        binding.exhibitToolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        });

    }

    private void initView() {
        binding.exhibitToolbar.inflateMenu(R.menu.index_toolbar_menu);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        binding.exhibitToolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
