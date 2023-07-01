package com.cdtde.chongdetang.base.view;

import android.util.SparseArray;


/**
 * {@link BaseAdapter} 和 {@link DialogBinder} 的绑定参数封装类.
 * @author John
 */
public class BindingConfig {

    /**
     * 参数映射.
     * @see ViewConfig#params
     */
    public final SparseArray<Object> params = new SparseArray<>();

    public BindingConfig() {
    }

    /**
     * 参数添加方法.
     * @see ViewConfig#add(Integer, Object)
     */
    public BindingConfig add(Integer id, Object param) {
        if (params.get(id) == null) {
            params.put(id, param);
        }
        return this;
    }
}
