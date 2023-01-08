package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemIndexCollectionBinding;
import com.cdtde.chongdetang.model.Collection;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 16:02
 * @Version 1
 */
public class IndexCollectionAdapter extends BaseAdapter<Collection> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ItemIndexCollectionBinding binding = ItemIndexCollectionBinding.bind(holder.itemView);
        Collection collection = data.get(position);
        binding.setCollection(collection);
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
