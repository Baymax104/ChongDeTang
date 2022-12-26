package com.cdtde.chongdetang.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.databinding.ItemAddressBinding;
import com.cdtde.chongdetang.model.Address;
import com.cdtde.chongdetang.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<Address> data;

    public AddressAdapter(List<Address> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = data.get(position);
        holder.binding.addressItemName.setText(address.getName());
        holder.binding.addressItemPosition.setText(address.getPosition());
        holder.binding.addressItemPhone.setText(address.getPhone());
        holder.binding.addressItemDetail.setText(address.getDetailAddress());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemAddressBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAddressBinding.bind(itemView);
        }
    }
}
