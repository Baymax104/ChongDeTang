package com.cdtde.chongdetang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemExhibitCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 23:13
 * @Version 1
 */
public class ExhibitCollectionAdapter extends BaseAdapter<Collection> {

    public interface OnMoreClickListener {
        void onClick(View view, Collection collection);
    }

    private OnMoreClickListener onMoreClickListener = (view, collection) -> {
    };

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exhibit_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Collection collection = data.get(position);
        ItemExhibitCollectionBinding binding = ((ViewHolder) holder).binding;
        binding.setCollection(collection);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(collection));
        binding.more.setOnClickListener(v -> onMoreClickListener.onClick(v, collection));
    }


    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    private static class ViewHolder extends BaseViewHolder {
        ItemExhibitCollectionBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemExhibitCollectionBinding.bind(itemView);
        }
    }
}
