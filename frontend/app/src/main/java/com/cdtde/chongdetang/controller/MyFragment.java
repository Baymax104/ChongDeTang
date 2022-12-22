package com.cdtde.chongdetang.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentMyBinding;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:05
 * @Version 1
 */
public class MyFragment extends Fragment {


    private FragmentMyBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        initView();

        binding.myToolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.my_settings) {
                Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    private void initView() {
        binding.myToolbar.inflateMenu(R.menu.my_toolbar_menu);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        binding.myToolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
