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
                SettingActivity.actionStart(getContext());
            }
            return true;
        });

        setListener();//选项监听事件



    }

    private void setListener() {
        binding.myAppointment.setOnClickListener(v -> Toast.makeText(getContext(), "我的预约", Toast.LENGTH_SHORT).show());
        binding.myCollection.setOnClickListener(v -> Toast.makeText(getContext(), "我的收藏", Toast.LENGTH_SHORT).show());
        binding.myShopping.setOnClickListener(v -> Toast.makeText(getContext(), "我的购物车", Toast.LENGTH_SHORT).show());
        binding.myOrder.setOnClickListener(v -> Toast.makeText(getContext(), "我的订单", Toast.LENGTH_SHORT).show());
        binding.myAddress.setOnClickListener(v -> Toast.makeText(getContext(), "收货地址", Toast.LENGTH_SHORT).show());
        binding.myNotice.setOnClickListener(v -> Toast.makeText(getContext(), "消息通知", Toast.LENGTH_SHORT).show());
        binding.myFeedback.setOnClickListener(v -> Toast.makeText(getContext(), "意见反馈", Toast.LENGTH_SHORT).show());
    }

    private void initView() {
        binding.myToolbar.inflateMenu(R.menu.my_toolbar_menu);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        binding.myToolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);



    }
}
