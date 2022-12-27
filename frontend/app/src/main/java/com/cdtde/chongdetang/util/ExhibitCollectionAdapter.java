package com.cdtde.chongdetang.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemExhibitCollectionBinding;
import com.cdtde.chongdetang.model.Collection;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/26 23:13
 * @Version 1
 */
public class ExhibitCollectionAdapter extends RecyclerView.Adapter<ExhibitCollectionAdapter.ViewHolder> {

    private List<Collection> data;

    public ExhibitCollectionAdapter(List<Collection> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exhibit_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection = data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemExhibitCollectionBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemExhibitCollectionBinding.bind(itemView);
        }
    }
}
