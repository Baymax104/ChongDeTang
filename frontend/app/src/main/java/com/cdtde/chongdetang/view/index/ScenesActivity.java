package com.cdtde.chongdetang.view.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivitySceneBinding;
import com.cdtde.chongdetang.util.WindowUtil;

public class ScenesActivity extends AppCompatActivity {
    private ActivitySceneBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scene);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ScenesActivity.class);
        context.startActivity(intent);
    }
}
