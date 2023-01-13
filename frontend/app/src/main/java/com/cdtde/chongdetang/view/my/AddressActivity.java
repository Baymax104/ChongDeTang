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
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityAddressBinding;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.AddressAdapter;
import com.cdtde.chongdetang.viewModel.my.AddressViewModel;

public class AddressActivity extends AppCompatActivity {
    private ActivityAddressBinding binding;
    private AddressViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        vm = new ViewModelProvider(this).get(AddressViewModel.class);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        binding.setLifecycleOwner(this);
        binding.setAdapter(new AddressAdapter());
        binding.setViewModel(vm);
    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
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
