package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemShopProductBinding;
import com.cdtde.chongdetang.entity.Product;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/8 20:51
 * @Version 1
 */
public class ShopProductAdapter extends BaseAdapter<Product> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemShopProductBinding binding = ItemShopProductBinding.bind(holder.itemView);
        Product product = data.get(position);
        binding.setProduct(product);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends BaseViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
