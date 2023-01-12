package com.cdtde.chongdetang.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentMyBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.MyViewModel;

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

        binding.setUser(vm.getUser());

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.my_settings) {
                SettingActivity.actionStart(getContext());
            }
            return true;
        });

        binding.userIcon.setOnClickListener(v -> UserInfoActivity.actionStart(getContext()));

        binding.appointmentEntry.setOnClickListener(v -> AppointmentActivity.actionStart(getContext()));

        binding.collectionEntry.setOnClickListener(v -> Toast.makeText(getContext(), "我的收藏", Toast.LENGTH_SHORT).show());

        binding.shoppingEntry.setOnClickListener(v -> Toast.makeText(getContext(), "我的购物车", Toast.LENGTH_SHORT).show());

        binding.orderEntry.setOnClickListener(v -> Toast.makeText(getContext(), "我的订单", Toast.LENGTH_SHORT).show());

        binding.addressEntry.setOnClickListener(v -> AddressActivity.actionStart(getContext()));

        binding.noticeEntry.setOnClickListener(v -> Toast.makeText(getContext(), "消息通知", Toast.LENGTH_SHORT).show());

        binding.feedbackEntry.setOnClickListener(v -> FeedbackActivity.actionStart(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
