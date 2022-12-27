package com.cdtde.chongdetang.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cdtde.chongdetang.model.Collection;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.LocalImageInfo;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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




}
