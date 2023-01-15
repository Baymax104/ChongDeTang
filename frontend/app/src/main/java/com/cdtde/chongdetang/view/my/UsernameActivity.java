package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActicityUsernameBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.regex.Pattern;

public class UsernameActivity extends AppCompatActivity {
    private ActicityUsernameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.acticity_username);
        binding.setLifecycleOwner(this);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        binding.setValidity(true);

        binding.confirm.setOnClickListener(v -> {
            String content = binding.nameEdit.getText().toString();
            if (validate(content)) {
                LiveEventBus.get("username", String.class).post(content);
                finish();
            } else {
                binding.setValidity(false);
            }
        });
    }

    private boolean validate(String content) {
        String pattern = "^[a-zA-Z\\d\\u4e00-\\u9fa5]+$";
        boolean isMatch = Pattern.matches(pattern, content);

        int length = content.length();
        boolean lengthValidity = length >= 2 && length <= 12;

        return isMatch && lengthValidity;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UsernameActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
