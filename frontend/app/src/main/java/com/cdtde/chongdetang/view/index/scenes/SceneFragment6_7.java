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

import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentScene67Binding;
import com.cdtde.chongdetang.repository.AppKey;

public class SceneFragment6_7 extends Fragment {
    private FragmentScene67Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScene67Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        String url = AppKey.COS_URL + "/img/scenes/img6_7.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(binding.sceneImg67);

        binding.sceneFab67Right.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment6_7_to_sceneFragment6_8);
        });
        binding.sceneFab67Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment6_7_to_sceneFragment6_6);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}