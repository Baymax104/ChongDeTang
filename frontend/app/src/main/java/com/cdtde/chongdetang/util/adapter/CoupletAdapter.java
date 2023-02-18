package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemCoupletBinding;
import com.cdtde.chongdetang.entity.News;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/18 19:15
 * @Version 1
 */
public class CoupletAdapter extends BaseAdapter<News> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_couplet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        News news = data.get(position);
        ((ViewHolder) holder).binding.setNews(news);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(news));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    private static class ViewHolder extends BaseViewHolder {
        ItemCoupletBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemCoupletBinding.bind(itemView);
        }
    }
}
