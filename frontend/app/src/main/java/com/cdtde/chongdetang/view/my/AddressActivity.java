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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cdtde.chongdetang.model.Address;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.util.AddressAdapter;
import com.cdtde.chongdetang.databinding.ActivityMyAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private ActivityMyAddressBinding binding;
//    private RecyclerView addressList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
        initRecylist();
        setListener();//设置监听事件
    }

    private void initRecylist() {
        //地址list 设置适配器
        List<Address> dataList = new ArrayList<>();
        //设置假数据
        for (int i=0;i<5;i++){
            Address tmp =new Address();
            dataList.add(tmp);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        AddressAdapter adapter = new AddressAdapter(dataList);
        binding.myAddressList.setAdapter(adapter);
        binding.myAddressList.setLayoutManager(manager);
    }

    private void setListener() {
        binding.myAddressAddBtn.setOnClickListener(v -> {
            Toast.makeText(this, "添加新地址", Toast.LENGTH_SHORT).show();
        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
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
