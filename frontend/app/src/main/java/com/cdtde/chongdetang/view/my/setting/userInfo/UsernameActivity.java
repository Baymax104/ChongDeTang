package com.cdtde.chongdetang.view.my.setting.userInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActicityUsernameBinding;
import com.cdtde.chongdetang.util.ValidateUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

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
            if (ValidateUtil.validateUsername(content)) {
                LiveEventBus.get("UsernameActivity-username", String.class).post(content);
                finish();
            } else {
                binding.setValidity(false);
            }
        });
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
