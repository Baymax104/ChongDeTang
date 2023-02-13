package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;

import com.cdtde.chongdetang.databinding.ItemIndexCultureBinding;
import com.cdtde.chongdetang.entity.Culture;


public class CultureAdapter extends BaseAdapter<Culture>{

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_culture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Culture culture = data.get(position);
        ((ViewHolder) holder).binding.setCulture(culture);
        ((ViewHolder) holder).binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(culture));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }



    private static class ViewHolder extends BaseViewHolder {
        ItemIndexCultureBinding binding;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemIndexCultureBinding.bind(itemView);
            view =itemView;

        }
    }

}
