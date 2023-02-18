package com.cdtde.chongdetang.view.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ActivityProductBinding;
import com.cdtde.chongdetang.entity.Product;
import com.cdtde.chongdetang.repository.AppKey;
import com.cdtde.chongdetang.util.WindowUtil;
import com.cdtde.chongdetang.viewModel.shop.ProductViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;

    private ProductViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(ProductViewModel.class);
        binding.setViewModel(vm);

        WindowUtil.initActivityWindow(binding.toolbar, this, true, true);

        LiveEventBus.get("ShopFragment-onItemClick", Product.class)
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
    }

    public static void actionStart(Context context) {
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