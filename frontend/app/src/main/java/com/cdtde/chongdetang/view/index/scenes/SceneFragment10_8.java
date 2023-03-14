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
import com.cdtde.chongdetang.databinding.FragmentScene108Binding;
import com.cdtde.chongdetang.repository.AppKey;

public class SceneFragment10_8 extends Fragment {

    private FragmentScene108Binding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentScene108Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        String url = AppKey.COS_URL +  "/img/scenes/img10_8.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(binding.sceneImg108);

        binding.sceneFab108Right.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment10_8_to_sceneFragment10_9);
        });
        binding.sceneFab108Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment10_8_to_sceneFragment10_7);
        });
        binding.sceneFab108Locate.setOnClickListener(v -> {
            Toast.makeText(getContext(), "前往商德匾2号展厅", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment10_8_to_sceneFragment11_1);
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}