package com.cdtde.chongdetang.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cdtde.chongdetang.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/24 22:24
 * @Version 1
 */
public class SearchTagAdapter extends TagAdapter<String> {
    private Context context;

    public SearchTagAdapter(Context context, List<String> datas) {
        super(datas);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout flowLayout, int i, String s) {
        TextView text = (TextView) LayoutInflater.from(context).inflate(R.layout.item_search_tag, flowLayout, false);
        text.setText(s);
        return text;
    }

}
