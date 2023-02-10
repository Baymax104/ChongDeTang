package com.cdtde.chongdetang.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.UriUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.repository.AppKey;
import com.cdtde.chongdetang.util.adapter.BannerAdapter;
import com.cdtde.chongdetang.util.adapter.BaseAdapter;
import com.cdtde.chongdetang.util.adapter.FragmentAdapter;
import com.cdtde.chongdetang.util.adapter.SearchTagAdapter;
import com.youth.banner.Banner;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @Description DataBinding自定义绑定的工具类，由DataBinding自动调用
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

    @BindingAdapter(value = {"img_url", "img_cos"}, requireAll = false)
    public static void setImgUrl(ImageView view, String url, Boolean cos) {
        if (url == null) {
            Glide.with(view)
                    .load(R.drawable.loading)
                    .into(view);
        } else {
            if (cos) {
                String path = AppKey.COS_URL + "/" + url;
                Glide.with(view)
                        .asBitmap()
                        .load(path)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.loading)
                        .into(new ImageViewTarget<Bitmap>(view) {
                            @Override
                            protected void setResource(@Nullable Bitmap resource) {
                                if (resource != null) {
                                    int imgWidth = resource.getWidth();
                                    int imgHeight = resource.getHeight();
                                    int scaleWidth = view.getWidth();
                                    int scaleHeight = scaleWidth * imgHeight / imgWidth;
                                    Bitmap scale = ImageUtils.scale(resource, scaleWidth, scaleHeight);
                                    view.setImageBitmap(scale);
                                }
                            }
                        });
            } else {
                Glide.with(view)
                        .asBitmap()
                        .load(url)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.loading)
                        .override(533, 640)
                        .into(view);
            }
        }
    }

    @BindingAdapter("src")
    public static void setImg(ImageView view, String uriString) {
        Uri uri = Uri.parse(uriString);
        view.setImageURI(uri);
    }

    @BindingAdapter({"tag_data", "relate"})
    public static void setSearchTag(TagFlowLayout tagFlowLayout, List<String> data, EditText editText) {
        SearchTagAdapter adapter = new SearchTagAdapter(data);
        tagFlowLayout.setAdapter(adapter);
        tagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            editText.setText(data.get(position));
            return true;
        });
    }

    @BindingAdapter(value = {"fragment_adapter", "fragments", "fragment_page"}, requireAll = false)
    public static void setFragments(ViewPager2 viewPager, FragmentAdapter adapter, List<Fragment> data, int page) {
        adapter.setFragments(data);
        viewPager.setAdapter(adapter);
        if (page == 1 || page == 2) {
            viewPager.setCurrentItem(page - 1, false);
        }
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

    @BindingAdapter("web_url")
    public static void setWebUrl(WebView view, String url) {
        view.loadUrl(url);
    }

    public static void bindPasswordEdit(View view, EditText edit) {
        ImageView img = (ImageView) view;
        if (edit.getInputType() == 128) {
            edit.setInputType(129);
            img.setImageResource(R.drawable.visible);
        } else {
            edit.setInputType(128);
            img.setImageResource(R.drawable.invisible);
        }
        edit.setSelection(edit.getText().length());
    }

    @BindingConversion
    public static String convertDouble(Double value) {
        return value != null ? String.valueOf(value) : "0.0";
    }

    @BindingConversion
    public static String convertDate(Date date) {
        if (date == null) {
            return "未填写";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }

    @BindingConversion
    public static String convertAppointStateToString(Appointment.State state) {
        if (state == Appointment.State.SUCCESS) {
            return "预约成功";
        } else if (state == Appointment.State.PROCESSING) {
            return "预约中";
        } else if (state == Appointment.State.FAIL) {
            return "预约失败";
        }
        return "";
    }

}
