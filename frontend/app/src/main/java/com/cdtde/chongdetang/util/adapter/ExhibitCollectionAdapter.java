package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemExhibitCollectionBinding;
import com.cdtde.chongdetang.entity.Collection;

import io.reactivex.annotations.NonNull;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 23:13
 * @Version 1
 */
public class ExhibitCollectionAdapter extends BaseAdapter<Collection> {


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exhibit_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Collection collection = data.get(position);
        ((ViewHolder) holder).binding.setCollection(collection);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ViewHolder extends BaseViewHolder {
        ItemExhibitCollectionBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemExhibitCollectionBinding.bind(itemView);
        }
    }
}
