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
import com.cdtde.chongdetang.databinding.FragmentScene33Binding;
import com.cdtde.chongdetang.repository.AppKey;

public class SceneFragment3_3 extends Fragment {
    private FragmentScene33Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentScene33Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        String url = AppKey.COS_URL +  "/img/scenes/img3_3.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(binding.sceneImg33);

        binding.sceneFab33Right.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment3_3_to_sceneFragment3_2);
        });
        binding.sceneFab33Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment3_3_to_sceneFragment3_4);
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}