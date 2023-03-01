package com.cdtde.chongdetang.view.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityProductBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.exception.WebException;
import com.cdtde.chongdetang.repository.AppKey;
import com.cdtde.chongdetang.util.DialogUtil;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.view.my.login.LoginActivity;
import com.cdtde.chongdetang.viewModel.shop.ProductViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;

    private ProductViewModel vm;

    private LoadingPopupView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(ProductViewModel.class);
        binding.setViewModel(vm);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        loading = (LoadingPopupView) DialogUtil.create(this, LoadingPopupView.class, new XPopup.Builder(this)
                .dismissOnTouchOutside(false));

        LiveEventBus.get("ProductActivity-getData", Product.class)
                .observeSticky(this, product -> {
                    vm.setProduct(product);
                    // 加载图片
                    String path = AppKey.COS_URL + "/" + product.getPhoto();
                    Glide.with(this)
                            .asBitmap()
                            .load(path)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.loading)
                            .override(ConvertUtils.dp2px(350))
                            .into(binding.img);
                });

        LiveEventBus.get("ShopRepository-requestAddShopping", WebException.class)
                .observe(this, e -> {
                    loading.smartDismiss();
                    if (e.isSuccess()) {
                        ToastUtils.showShort("添加成功！");
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                });

        LiveEventBus.get("ShopRepository-requestAddUserCollect", WebException.class)
                        .observe(this, e -> {
                            if (e.isSuccess()) {
                                ToastUtils.showShort("收藏成功");
                            } else {
                                ToastUtils.showShort(e.getMessage());
                            }
                        });

        binding.shoppingEntry.setOnClickListener(v -> ShoppingActivity.actionStart(this));

        binding.addShopping.setOnClickListener(v -> {
            loading.show();
            vm.addShopping();
        });

        binding.collect.setOnClickListener(v -> {
            if (vm.isLogin()) {
                vm.addUserCollect();
            } else {
                LoginActivity.actionStart(this);
            }
        });
    }

    public static void actionStart(Context context, Product product) {
        LiveEventBus.get("ProductActivity-getData", Product.class).post(product);
        Intent intent = new Intent(context, ProductActivity.class);
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