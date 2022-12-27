package com.cdtde.chongdetang.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.model.Collection;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.LocalImageInfo;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2022/12/27 17:18
 * @Version 1
 */
public class BindingAdapterUtils {


    @BindingAdapter("src")
    public static void setImg(ImageView view, int id) {
        view.setImageResource(id);
    }

    @BindingAdapter("imgUrl")
    public static void setImgUrl(ImageView view, String url) {
        // 网络请求图片
    }

    @BindingAdapter("bannerData")
    public static void setBannerData(XBanner banner, List<LocalImageInfo> data) {
        banner.setBannerData(data);
    }

    @BindingAdapter({"recyclerAdapter", "recyclerData"})
    public static void setIndexRecyclerView(RecyclerView view, IndexCollectionAdapter adapter, List<Collection> data) {
        adapter.setData(data);
        view.setAdapter(adapter);
    }

    @BindingAdapter({"tagData"})
    public static void setSearchTag(TagFlowLayout tagFlowLayout, List<String> data) {
        SearchTagAdapter adapter = new SearchTagAdapter(data);
        tagFlowLayout.setAdapter(adapter);
    }
}
