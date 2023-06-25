package com.cdtde.chongdetang.base.view;

import android.util.SparseArray;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/23 18:16
 * @Version 1
 */
public class ViewConfig {

    public final Integer layout;

    public final SparseArray<Object> params = new SparseArray<>();

    public ViewConfig(Integer layout) {
        this.layout = layout;
    }

    public ViewConfig add(Integer id, Object param) {
        if (params.get(id) == null) {
            params.put(id, param);
        }
        return this;
    }
}
