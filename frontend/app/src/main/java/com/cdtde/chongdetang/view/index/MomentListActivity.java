package com.cdtde.chongdetang.view.index;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityMomentListBinding;

public class MomentListActivity extends AppCompatActivity {

    private ActivityMomentListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moment_list);
        binding.setLifecycleOwner(this);


    }
}