package com.cdtde.chongdetang.view.my.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityAddressDetailBinding;
import com.cdtde.chongdetang.entity.Address;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.my.AddressDetailViewModel;
import com.github.gzuliyujiang.wheelpicker.AddressPicker;
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class AddressDetailActivity extends AppCompatActivity {

    private ActivityAddressDetailBinding binding;

    private AddressDetailViewModel vm;

    private AddressPicker picker;

    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_detail);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(AddressDetailViewModel.class);
        binding.setViewModel(vm);
        WindowUtil.initActivityWindow(binding.toolbar, this, true);

        LiveEventBus.get("AddressActivity-onItemClick", Address.class)
                .observeSticky(this, address -> vm.setAddress(address));

        LiveEventBus.get("MyRepository-updateAddress", Boolean.class)
                .observe(this, aBoolean -> {
                    loading.smartDismiss();
                    if (aBoolean) {
                        LiveEventBus.get("AddressDetailActivity-updateAddress", Boolean.class).post(true);
                        finish();
                    }
                });

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false));

        picker = new AddressPicker(this);
        picker.setAddressMode(AddressMode.PROVINCE_CITY);
        picker.setTitle("选择城市");
        picker.getWheelLayout().setSelectedTextBold(true);
        picker.setOnAddressPickedListener((province, city, county) ->
                vm.setArea(province.getName(), city.getName())
        );

        binding.selectAddress.setOnClickListener(v -> picker.show());

        binding.delete.setOnClickListener(v -> {

        });

        binding.save.setOnClickListener(v -> {
            vm.updateAddress();
            loading.show();
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddressDetailActivity.class);
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