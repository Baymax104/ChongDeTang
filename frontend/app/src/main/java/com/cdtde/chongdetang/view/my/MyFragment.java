package com.cdtde.chongdetang.view.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentMyBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.setting.LoginActivity;
import com.cdtde.chongdetang.view.my.setting.SettingActivity;
import com.cdtde.chongdetang.view.my.setting.userInfo.UserInfoActivity;
import com.cdtde.chongdetang.viewModel.my.MyViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/21 22:05
 * @Version 1
 */
public class MyFragment extends Fragment {

    private FragmentMyBinding binding;
    private MyViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        binding.toolbar.inflateMenu(R.menu.my_toolbar);
        WindowUtil.initFragmentWindow(binding.toolbarLayout, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        binding.setViewModel(vm);

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.my_settings) {
                SettingActivity.actionStart(getContext());
            }
            return true;
        });

        LiveEventBus.get("logout", Boolean.class)
                        .observe(this, aBoolean -> {
                            if (aBoolean) {
                                vm.logout();
                            }
                        });

        binding.userIcon.setOnClickListener(v -> {
            if (vm.isLogin()) {
                UserInfoActivity.actionStart(getContext());
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.appointmentEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                AppointmentActivity.actionStart(getContext());
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.collectionEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                ToastUtils.showShort("进入收藏");
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.shoppingEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                ToastUtils.showShort("进入购物车");
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.orderEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                ToastUtils.showShort("进入订单");
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.addressEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                AddressActivity.actionStart(getContext());
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.noticeEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                ToastUtils.showShort("进入通知");
            } else {
                LoginActivity.actionStart(getContext());
            }
        });

        binding.feedbackEntry.setOnClickListener(v -> {
            if (vm.isLogin()) {
                FeedbackActivity.actionStart(getContext());
            } else {
                LoginActivity.actionStart(getContext());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
