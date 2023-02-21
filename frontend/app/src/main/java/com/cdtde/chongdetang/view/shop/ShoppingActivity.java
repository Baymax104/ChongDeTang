package com.cdtde.chongdetang.view.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ToastUtils;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityShoppingBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.util.adapter.ShoppingAdapter;
import com.cdtde.chongdetang.viewModel.shop.ShoppingViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class ShoppingActivity extends AppCompatActivity {

    private ActivityShoppingBinding binding;

    private ShoppingViewModel vm;

    private LoadingPopupView loading;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShoppingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(ShoppingViewModel.class);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        binding.setViewModel(vm);

        ShoppingAdapter adapter = new ShoppingAdapter();
        adapter.setOnItemClickListener(data -> {
            Product product = data.getShopping().getProduct();
            LiveEventBus.get("ShopFragment-onItemClick", Product.class).post(product);
            ProductActivity.actionStart(this);
        });
        adapter.setOnCheckChangeListener(new ShoppingAdapter.ItemListener() {
            @Override
            public void onCheckChange(ShoppingViewModel.CheckedShopping checkedShopping, boolean isChecked) {
                boolean isAllSelect = vm.checkOne(checkedShopping, isChecked);
                binding.allSelect.setChecked(isAllSelect);
            }

            @Override
            public void onUpdateNumberClick(ShoppingViewModel.CheckedShopping checkedShopping, boolean isAdd) {
                if (isAdd) {
                    boolean isFull = vm.addProductNumber(checkedShopping);
                    if (isFull) {
                        ToastUtils.showShort("商品不能再增加了");
                    } else {
                        loading.show();
                    }
                } else {
                    boolean isEmpty = vm.subtractProductNumber(checkedShopping);
                    if (isEmpty) {
                        ToastUtils.showShort("商品不能再减少了");
                    } else {
                        loading.show();
                    }
                }
            }

            @Override
            public void onDeleteClick(ShoppingViewModel.CheckedShopping checkedShopping) {
                vm.deleteShopping(checkedShopping);
                loading.show();
            }
        });

        binding.setAdapter(adapter);

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false)).show();

        LiveEventBus.get("ShopRepository-requestShopping", Boolean.class)
                .observe(this, aBoolean -> {
                    loading.smartDismiss();
                    if (aBoolean) {
                        vm.refreshShopping();
                        vm.checkAll(true);
                    }
                });

        LiveEventBus.get("ShopRepository-updateShoppingNumber", Boolean.class)
                .observe(this, aBoolean -> {
                    loading.smartDismiss();
                    if (aBoolean) {
                        vm.refreshPrice();
                    }
                });

        LiveEventBus.get("ShopRepository-deleteShopping", Boolean.class)
                .observe(this, aBoolean -> {
                    if (aBoolean) {
                        vm.updateShopping();
                    }
                });

        binding.allSelect.setOnClickListener(v -> {
            CheckBox box = (CheckBox) v;
            vm.checkAll(box.isChecked());
        });
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