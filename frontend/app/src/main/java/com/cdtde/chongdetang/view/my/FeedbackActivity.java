package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityFeedbackBinding;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.viewModel.my.FeedbackViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class FeedbackActivity extends AppCompatActivity {
    private ActivityFeedbackBinding binding;
    private FeedbackViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider(this).get(FeedbackViewModel.class);
        binding.setViewModel(vm);
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        setListener();//设置监听事件

        LiveEventBus.get("MyRepository-requestFeedbackCommit", WebException.class)
                .observe(this, e -> {
                    if (e.isSuccess()) {
                        finish();
                    } else {
                        ToastUtils.showShort("反馈失败：" + e.getMessage());
                    }
                });
    }

    private void setListener() {
        binding.feedbackCommit.setOnClickListener(v -> vm.updateFeedback());
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    private void initViews() {
        setSupportActionBar(binding.settingsToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
