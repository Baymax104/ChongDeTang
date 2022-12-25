package com.cdtde.chongdetang.view.my.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cdtde.chongdetang.model.CustomerAddress;
import com.cdtde.chongdetang.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private View view;
    private List<CustomerAddress> dataList;
    private Context context;

    public AddressAdapter(List<CustomerAddress> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList!=null?dataList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        addressViewHolder addressHolder=null;
        //判断是否第一次加载
        if(convertView == null){
            addressHolder = new addressViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_address_item, null);
            addressHolder.name=convertView.findViewById(R.id.myAddressItem_name);
            addressHolder.phone=convertView.findViewById(R.id.myAddressItem_phone);
            addressHolder.position=convertView.findViewById(R.id.myAddressItem_position);
            addressHolder.detail=convertView.findViewById(R.id.myAddressItem_detail);
            convertView.setTag(addressHolder);
        }else{
            addressHolder = (addressViewHolder) convertView.getTag();
        }
        //设置数据
        CustomerAddress addItem=dataList.get(position);
        addressHolder.name.setText(addItem.getName());
        addressHolder.phone.setText(addItem.getPhone());
        addressHolder.detail.setText(addItem.getDetailAddress());
        addressHolder.position.setText(addItem.getPosition());
        return convertView;
    }
    public class addressViewHolder{
        public TextView name;
        public TextView phone;
        public TextView position;
        public TextView detail;
    }
}
