package com.cdtde.chongdetang.view.index.Scenes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentScene54Binding;
import com.cdtde.chongdetang.databinding.FragmentScene61Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SceneFragment6_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SceneFragment6_1 extends Fragment {
    private FragmentScene61Binding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SceneFragment6_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SceneFragment6_1.
     */
    // TODO: Rename and change types and number of parameters
    public static SceneFragment6_1 newInstance(String param1, String param2) {
        SceneFragment6_1 fragment = new SceneFragment6_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentScene61Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sceneFab61Down.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment6_1_to_sceneFragment6_6);
        });
        binding.sceneFab61Locate.setOnClickListener(v -> {
            Toast.makeText(getContext(), "前往堂号匾3号展厅", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment6_1_to_sceneFragment7_1);
        });
        binding.sceneFab61Right.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment6_1_to_sceneFragment6_2);
        });
        binding.sceneFab61Left.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_sceneFragment6_1_to_sceneFragment6_9);
        });
    }
}