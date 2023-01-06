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
import androidx.recyclerview.widget.LinearLayoutManager;


import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.util.adapter.AppointmentAdapter;
import com.cdtde.chongdetang.databinding.ActivityMyAppointmentBinding;
import com.cdtde.chongdetang.viewModel.AppointmentViewModel;



public class AppointmentActivity extends AppCompatActivity {
    private ActivityMyAppointmentBinding binding;
    private AppointmentViewModel vm;
    private AppointmentAdapter adapter;
    //    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAppointmentBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider(this).get(AppointmentViewModel.class);
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        initList();
        setListener();//设置监听事件
    }

    private void initList() {

        binding.myAppointmentAppointList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new AppointmentAdapter(vm.getDataList());
        binding.myAppointmentAppointList.setAdapter(adapter);
    }

    private void setListener() {

    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AppointmentActivity.class);
        context.startActivity(intent);
    }

    private void initViews() {
        setSupportActionBar(binding.myAppointmentToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        input=findViewById(R.id.feedback_content);
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
