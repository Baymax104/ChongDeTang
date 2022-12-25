package com.cdtde.chongdetang.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentIndexBinding;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 20:52
 * @Version 1
 */
public class IndexFragment extends Fragment {
    private FragmentIndexBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        initView();

        binding.indexToolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.index_search) {
                SearchActivity.actionStart(getContext());
            }
            return true;
        });

    }

    private void initView() {
        binding.indexToolbar.inflateMenu(R.menu.index_toolbar_menu);

        // 设置toolbar适应状态栏
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        binding.indexToolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
