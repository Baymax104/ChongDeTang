package com.cdtde.chongdetang.view.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cdtde.chongdetang.model.CustomerAddress;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.view.my.adapters.ListInScroll;
import com.cdtde.chongdetang.view.my.adapters.AddressAdapter;
import com.cdtde.chongdetang.databinding.ActivityMyAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class MyAddressActivity extends AppCompatActivity {
    private ActivityMyAddressBinding binding;
//    private RecyclerView addressList;
    private ListInScroll addressList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        initRecylist(view);
        setListener();//设置监听事件
    }

    private void initRecylist(View view) {
        //地址list 设置适配器
        addressList=view.findViewById(R.id.myAddress_list);
        List<CustomerAddress> dataList = null;
        dataList=new ArrayList<>();
        //设置假数据
        for (int i=0;i<5;i++){
            CustomerAddress tmp =new CustomerAddress();
            dataList.add(tmp);
        }
//        MyAdapter adapter=new MyAdapter(dataList);
        AddressAdapter adapter = new AddressAdapter(dataList,this);
        addressList.setAdapter(adapter);
    }

    private void setListener() {
        binding.myAddressAddBtn.setOnClickListener(v -> {
            Toast.makeText(this, "添加新地址", Toast.LENGTH_SHORT).show();
        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyAddressActivity.class);
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