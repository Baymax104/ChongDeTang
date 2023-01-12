package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityAppointmentBinding;
import com.cdtde.chongdetang.util.adapter.AppointmentAdapter;
import com.cdtde.chongdetang.viewModel.my.AppointmentViewModel;



public class AppointmentActivity extends AppCompatActivity {
    private ActivityAppointmentBinding binding;
    private AppointmentViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointment);
        vm = new ViewModelProvider(this).get(AppointmentViewModel.class);
        binding.setLifecycleOwner(this);

        initWindow();

        binding.setAdapter(new AppointmentAdapter());
        binding.setViewModel(vm);

    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AppointmentActivity.class);
        context.startActivity(intent);
    }

    private void initWindow() {
        setSupportActionBar(binding.toolbar);
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
