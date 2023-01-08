package com.cdtde.chongdetang.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cdtde.chongdetang.util.adapter.BannerAdapter;
import com.cdtde.chongdetang.util.adapter.BaseAdapter;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.util.adapter.SearchTagAdapter;
import com.youth.banner.Banner;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/7 23:31
 * @Version 1
 */
public class BindingUtil {
    @BindingAdapter("src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("img_url")
    public static void serImgUrl(ImageView view, String url) {
        // 网络请求图片
    }

    @BindingAdapter({"tag_data"})
    public static void setSearchTag(TagFlowLayout tagFlowLayout, List<String> data) {
        SearchTagAdapter adapter = new SearchTagAdapter(data);
        tagFlowLayout.setAdapter(adapter);
    }

    @BindingAdapter({"fragment_adapter", "fragments"})
    public static void setFragments(ViewPager2 viewPager, FragmentAdapter adapter, List<Fragment> data) {
        adapter.setFragments(data);
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter({"recycler_adapter", "recycler_data"})
    public static <T> void setRecyclerView(RecyclerView view, BaseAdapter<T> adapter, List<T> data) {
        adapter.setData(data);
        view.setAdapter(adapter);
    }

    @BindingAdapter("banner_data")
    public static void setBannerData(Banner<Integer, BannerAdapter> banner, List<Integer> data) {
        banner.setAdapter(new BannerAdapter(data));
    }

}
