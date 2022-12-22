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

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:05
 * @Version 1
 */
public class MyFragment extends Fragment {

    private View view;

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        initView();

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.my_settings) {
                Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    private void initView() {
        toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.inflateMenu(R.menu.my_toolbar_menu);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        toolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);

    }
}
