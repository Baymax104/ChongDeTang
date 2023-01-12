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

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.FragmentScene45Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SceneFragment4_5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SceneFragment4_5 extends Fragment {
    private FragmentScene45Binding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SceneFragment4_5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SceneFragment4_5.
     */
    // TODO: Rename and change types and number of parameters
    public static SceneFragment4_5 newInstance(String param1, String param2) {
        SceneFragment4_5 fragment = new SceneFragment4_5();
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
        binding= FragmentScene45Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sceneFab45Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_sceneFragment4_5_to_sceneFragment4_4);
            }
        });
        binding.sceneFab45Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_sceneFragment4_5_to_sceneFragment4_6);
            }
        });
        binding.sceneFab45Locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "前往堂号匾匾展厅", Toast.LENGTH_SHORT).show();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_sceneFragment4_5_to_sceneFragment5_1);
            }
        });
    }
}