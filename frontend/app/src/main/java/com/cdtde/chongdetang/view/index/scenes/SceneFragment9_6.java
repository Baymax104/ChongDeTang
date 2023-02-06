package com.cdtde.chongdetang.view.index.scenes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentScene96Binding;
import com.cdtde.chongdetang.repository.AppKey;

public class SceneFragment9_6 extends Fragment {

    private FragmentScene96Binding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentScene96Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        String url = AppKey.COS_URL +  "/img/scenes/img9_6.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(binding.sceneImg96);

        binding.sceneFab96Right.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment9_6_to_sceneFragment9_7);
        });
        binding.sceneFab96Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment9_6_to_sceneFragment9_5);
        });
        binding.sceneFab96Locate.setOnClickListener(v -> {
            Toast.makeText(getContext(), "返回功德匾展厅", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment9_6_to_sceneFragment8_5);
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}