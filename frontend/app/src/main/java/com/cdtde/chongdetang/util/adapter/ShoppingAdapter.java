package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemShoppingProductBinding;
import com.cdtde.chongdetang.viewModel.shop.ShoppingViewModel;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/20 16:35
 * @Version 1
 */
public class ShoppingAdapter extends BaseAdapter<ShoppingViewModel.CheckedShopping> {

    public interface ItemListener {
        default void onCheckChange(ShoppingViewModel.CheckedShopping checkedShopping, boolean isChecked) {
        }

        default void onUpdateNumberClick(ShoppingViewModel.CheckedShopping checkedShopping, boolean isAdd) {
        }

        default void onDeleteClick(ShoppingViewModel.CheckedShopping checkedShopping) {
        }

    }

    private ItemListener itemListener = new ItemListener() {
    };

    public void setOnCheckChangeListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ShoppingViewModel.CheckedShopping checkedShopping = data.get(position);
        ((ViewHolder) holder).binding.setCheckedShopping(checkedShopping);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(checkedShopping));
        ((ViewHolder) holder).binding.check.setOnClickListener(v -> {
            CheckBox box = (CheckBox) v;
            itemListener.onCheckChange(checkedShopping, box.isChecked());
        });
        ((ViewHolder) holder).binding.add.setOnClickListener(v -> itemListener.onUpdateNumberClick(checkedShopping, true));
        ((ViewHolder) holder).binding.subtract.setOnClickListener(v -> itemListener.onUpdateNumberClick(checkedShopping, false));
        ((ViewHolder) holder).binding.delete.setOnClickListener(v -> itemListener.onDeleteClick(checkedShopping));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    private static class ViewHolder extends BaseViewHolder {

        ItemShoppingProductBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemShoppingProductBinding.bind(itemView);
        }
    }
}
