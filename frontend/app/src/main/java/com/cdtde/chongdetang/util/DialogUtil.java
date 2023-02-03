package com.cdtde.chongdetang.util;

import android.content.Context;
import android.util.Log;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.lang.reflect.Constructor;

/**
 * @Description 基于XPopup，封装对话框创建及方法调用
 * @Author John
 * @email
 * @Date 2023/1/10 17:27
 * @Version 1
 */
public class DialogUtil {

    private DialogUtil() {
    }

    /**
     * 反射调用构造器Constructor(Context context)创建XPopup对话框
     * @param context context
     * @param dialogType 对话框Class对象
     * @return 对话框对象
     * @param <T> 对话框类型
     */
    public static <T extends BasePopupView> BasePopupView create(Context context, Class<T> dialogType) {
        Constructor<T> constructor;
        BasePopupView dialog = null;

        if (dialogType == LoadingPopupView.class) {
            return new XPopup.Builder(context).asLoading();
        }

        try {
            constructor =  dialogType.getConstructor(Context.class);
            dialog = constructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DialogUtil-create", e.getMessage());
        }
        return new XPopup.Builder(context).asCustom(dialog);
    }

}
