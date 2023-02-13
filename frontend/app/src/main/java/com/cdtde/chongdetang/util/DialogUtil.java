package com.cdtde.chongdetang.util;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopupext.listener.TimePickerListener;

import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * @Description 基于XPopup，封装对话框创建及方法调用
 * @Author John
 * @email
 * @Date 2023/1/10 17:27
 * @Version 1
 */
public class DialogUtil {

    public interface TimePickerListenerAdapter extends TimePickerListener {
        @Override
        default void onTimeChanged(Date date) {
        }
        @Override
        default void onCancel() {
        }
    }

    private DialogUtil() {
    }

    /**
     * 反射调用构造器Constructor(Context context)创建XPopup对话框
     * @param context context
     * @param dialogType 对话框Class对象
     * @param builder 对话框builder对象
     * @return 对话框对象
     * @param <T> 对话框类型
     */
    public static <T extends BasePopupView> BasePopupView create(Context context, Class<T> dialogType, XPopup.Builder builder) {
        Constructor<T> constructor;
        BasePopupView dialog = null;
        if (builder == null) {
            builder = new XPopup.Builder(context);
        }

        if (dialogType == LoadingPopupView.class) {
            return builder.asLoading();
        }

        try {
            constructor =  dialogType.getConstructor(Context.class);
            dialog = constructor.newInstance(context);
        } catch (Exception e) {
            LogUtils.eTag("cdt-dialog-create", e);
        }
        return builder.asCustom(dialog);
    }

    public static <T extends BasePopupView> BasePopupView create(Context context, T view, XPopup.Builder builder) {
        if (builder == null) {
            builder = new XPopup.Builder(context);
        }
        return builder.asCustom(view);
    }

}
