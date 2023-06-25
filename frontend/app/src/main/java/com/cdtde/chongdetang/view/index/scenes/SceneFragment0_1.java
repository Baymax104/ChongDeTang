package com.cdtde.chongdetang.view.index.scenes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentScene01Binding;
import com.cdtde.chongdetang.repository.AppKey;


public class SceneFragment0_1 extends Fragment {
    private FragmentScene01Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScene01Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        String url = AppKey.COS_URL + "/img/scenes/img0_1.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(binding.sceneImg01);

        binding.sceneFab01Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment0_1_to_sceneFragment0_2);
        });
        binding.sceneFab01Right.setOnClickListener(v ->
                ToastUtils.showShort("文创馆暂未开放，敬请期待！！！！")
        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}