package com.cdtde.chongdetang.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.cdtde.chongdetang.R;
import com.cdtde.chongdetang.adapter.BannerAdapter;
import com.cdtde.chongdetang.adapter.BaseAdapter;
import com.cdtde.chongdetang.adapter.FragmentAdapter;
import com.cdtde.chongdetang.adapter.SearchTagAdapter;
import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.repository.AppKey;
import com.youth.banner.Banner;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Description DataBinding自定义绑定的工具类，由DataBinding自动调用
 * @Author John
 * @email
 * @Date 2023/1/7 23:31
 * @Version 1
 */
@SuppressLint("CheckResult")
public class BindingUtil {

    public static class ImageTarget extends ImageViewTarget<Bitmap> {

        public ImageTarget(ImageView view) {
            super(view);
        }

        @Override
        protected void setResource(@Nullable Bitmap resource) {
            if (resource != null) {
                int imgWidth = ConvertUtils.dp2px(resource.getWidth());
                int imgHeight = ConvertUtils.dp2px(resource.getHeight());
                int scaleWidth = view.getWidth();
                int scaleHeight = scaleWidth * imgHeight / imgWidth;
                Bitmap scale = ImageUtils.scale(resource, scaleWidth, scaleHeight);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = scaleHeight;
                view.setLayoutParams(params);
                view.setImageBitmap(scale);
            }
        }
    }

    @BindingAdapter(value = {"img_url", "img_cos", "img_rounded", "img_fix"}, requireAll = false)
    public static void setImgUrl(ImageView view, String url, boolean cos, boolean rounded, boolean fix) {
        if (url == null) {
            Glide.with(view)
                    .asBitmap()
                    .load(R.drawable.loading)
                    .into(view);
            return;
        }

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .skipMemoryCache(true);

        if (rounded) {
            // 调用transform后会将ImageView的scaleType覆盖，需要重新设置
            // 若在调用transform后在调用centerCrop，centerCrop也是调用transform，会将之前的设置覆盖
            options.transform(
                    new CenterCrop(),
                    new RoundedCorners(ConvertUtils.dp2px(10))
            );
        }
        if (cos) {
            String path = AppKey.COS_URL + "/" + url;
            RequestBuilder<Bitmap> request = Glide.with(view)
                    .asBitmap()
                    .load(path)
                    .apply(options);
            if (fix) {
                request.into(view);
            } else {
                request.into(new ImageTarget(view));
            }
            return;
        }
        RequestBuilder<Bitmap> request = Glide.with(view)
                .asBitmap()
                .load(url)
                .apply(options);
        if (fix) {
            request.into(view);
        } else {
            request.into(new ImageTarget(view));
        }
    }

    @BindingAdapter("img_uri")
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

    @BindingAdapter("sub_date")
    public static void setSubDate(TextView view, Date date) {
        String d = "";
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            String format1 = format.format(date);
            d = format1.substring(0, format1.length() - 3);
        }
        view.setText(d);
    }

    @BindingAdapter("sub_day")
    public static void setSubDay(TextView view, Date date) {
        String d = "";
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            String format1 = format.format(date);
            d = format1.substring(format1.length() - 2);
        }
        view.setText(d);
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
