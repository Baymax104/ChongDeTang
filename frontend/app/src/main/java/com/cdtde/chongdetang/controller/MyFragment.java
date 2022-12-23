package com.cdtde.chongdetang.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
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
    private LinearLayout yuYue,shouCang,gouWuChe,dingDan,diZhi,tongZhi,fanKui;

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
                SettingActivity.actionStart(getContext());
            }
            return true;
        });

        setListener();//选项监听事件



    }

    private void setListener() {
        yuYue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "我的预约", Toast.LENGTH_SHORT).show();
            }
        });
        shouCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "我的收藏", Toast.LENGTH_SHORT).show();
            }
        });
        gouWuChe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "我的购物车", Toast.LENGTH_SHORT).show();
            }
        });
        dingDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "我的订单", Toast.LENGTH_SHORT).show();
            }
        });
        diZhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "收货地址", Toast.LENGTH_SHORT).show();
            }
        });
        tongZhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "消息通知", Toast.LENGTH_SHORT).show();
            }
        });
        fanKui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "意见反馈", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.inflateMenu(R.menu.my_toolbar_menu);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        toolbar.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);

        //findViewById
        yuYue=view.findViewById(R.id.my_yuyue);
        shouCang=view.findViewById(R.id.my_shoucang);
        gouWuChe=view.findViewById(R.id.my_gouwuche);
        dingDan=view.findViewById(R.id.my_dingdan);
        diZhi=view.findViewById(R.id.my_dizhi);
        tongZhi=view.findViewById(R.id.my_tongzhi);
        fanKui=view.findViewById(R.id.my_fankui);


    }
}
