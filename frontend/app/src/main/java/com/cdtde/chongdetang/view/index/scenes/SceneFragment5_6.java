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
import com.cdtde.chongdetang.databinding.FragmentScene56Binding;
import com.cdtde.chongdetang.repository.AppKey;

public class SceneFragment5_6 extends Fragment {
    private FragmentScene56Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScene56Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        String url = AppKey.COS_URL + "/img/scenes/img5_6.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(binding.sceneImg56);

        binding.sceneFab56Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment5_6_to_sceneFragment5_5);
        });
        binding.sceneFab56Down.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment5_6_to_sceneFragment5_1);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}