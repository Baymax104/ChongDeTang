package com.cdtde.chongdetang.view.index.Scenes;

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

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentScene35Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SceneFragment3_5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SceneFragment3_5 extends Fragment {
    private FragmentScene35Binding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SceneFragment3_5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SceneFragment3_5.
     */
    // TODO: Rename and change types and number of parameters
    public static SceneFragment3_5 newInstance(String param1, String param2) {
        SceneFragment3_5 fragment = new SceneFragment3_5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentScene35Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sceneFab35Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_sceneFragment3_5_to_sceneFragment3_6);
            }
        });
        binding.sceneFab35Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_sceneFragment3_5_to_sceneFragment3_4);
            }
        });
        binding.sceneFab35Locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "前往坤德匾展厅", Toast.LENGTH_SHORT).show();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_sceneFragment3_5_to_sceneFragment4_1);
            }
        });
    }
}