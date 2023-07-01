package com.cdtde.chongdetang.base.view;

import android.util.SparseArray;

/**
 * {@link BaseActivity} 和 {@link BaseFragment} 的绑定参数封装类.
 * @author John
 */
public class ViewConfig {

    /**
     * 布局ID.
     */
    public final Integer layout;

    /**
     * 参数映射，键值对为参数ID-参数对象.
     */
    public final SparseArray<Object> params = new SparseArray<>();

    public ViewConfig(Integer layout) {
        this.layout = layout;
    }

    /**
     * 参数添加方法.
     * @param id 参数ID
     * @param param 参数对象
     * @return this 对象，便于链式调用
     */
    public ViewConfig add(Integer id, Object param) {
        if (params.get(id) == null) {
            params.put(id, param);
        }
        return this;
    }
}
