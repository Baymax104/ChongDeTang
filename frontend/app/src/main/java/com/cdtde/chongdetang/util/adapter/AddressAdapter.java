package com.cdtde.chongdetang.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.databinding.ItemAddressBinding;
import com.cdtde.chongdetang.entity.Address;

import java.util.List;

public class AddressAdapter extends BaseAdapter<Address> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Address address = data.get(position);
        ((ViewHolder) holder).binding.setAddress(address);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ViewHolder extends BaseViewHolder {

        ItemAddressBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAddressBinding.bind(itemView);
        }
    }
}
