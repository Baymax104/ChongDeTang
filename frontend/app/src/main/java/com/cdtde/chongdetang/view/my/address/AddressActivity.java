package com.cdtde.chongdetang.view.my.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.dataSource.web.WebException;
import com.cdtde.chongdetang.databinding.ActivityAddressBinding;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.AddressAdapter;
import com.cdtde.chongdetang.viewModel.my.AddressViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class AddressActivity extends AppCompatActivity {
    private ActivityAddressBinding binding;
    private AddressViewModel vm;

    private LoadingPopupView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        vm = new ViewModelProvider(this).get(AddressViewModel.class);
        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false)).show();

        LiveEventBus.get("MyRepository-getAllAddress", WebException.class)
                .observe(this, e -> {
                    loading.smartDismiss();
                    if (e.isSuccess()) {
                        vm.refreshAllAddress();
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });

        LiveEventBus.get("AddressDetailActivity-updateAddress", Boolean.class)
                .observe(this, aBoolean -> {
                            if (aBoolean) {
                                loading.show();
                                vm.updateAllAddress();
                            }
                        });

        LiveEventBus.get("AddressDetailActivity-deleteAddress", Boolean.class)
                        .observe(this, aBoolean -> {
                            if (aBoolean) {
                                loading.show();
                                vm.updateAllAddress();
                            }
                        });

        binding.setLifecycleOwner(this);
        binding.setViewModel(vm);

        AddressAdapter adapter = new AddressAdapter();
        adapter.setOnItemClickListener(data -> AddressDetailActivity.actionStart(this, data));
        binding.setAdapter(adapter);

        binding.addEntry.setOnClickListener(v -> AddressDetailActivity.actionStart(this, null));
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
