package com.cdtde.chongdetang.controller.my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cdtde.chongdetang.ItemClass.appointment;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.controller.my.adapters.appointmentAdapter;
import com.cdtde.chongdetang.databinding.ActivityMyAppointmentBinding;

import java.util.ArrayList;
import java.util.List;


public class myAppointmentActivity extends AppCompatActivity {
    private ActivityMyAppointmentBinding binding;
    private List<appointment> dataList;
    private appointmentAdapter adapter;
//    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        initList();
        setListener();//设置监听事件
    }

    private void initList() {

        dataList=new ArrayList<>();
        //设置假数据
        for (int i=0;i<5;i++){
            appointment tmp =new appointment();
            dataList.add(tmp);
        }
        binding.myAppointmentAppointList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new appointmentAdapter(dataList,this);
        binding.myAppointmentAppointList.setAdapter(adapter);
    }

    private void setListener() {

    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, myAppointmentActivity.class);
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
