package com.cdtde.chongdetang.util;

import android.content.Context;
import android.util.Log;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.lang.reflect.Constructor;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/10 17:27
 * @Version 1
 */
public class DialogUtil {

    private DialogUtil() {
    }

    public static <T extends BasePopupView> BasePopupView create(Context context, Class<T> dialogType) {
        Constructor<T> constructor;
        BasePopupView dialog = null;
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
