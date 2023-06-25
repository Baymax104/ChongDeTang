package com.cdtde.chongdetang.base.view;

import android.util.SparseArray;


/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/6/8 18:46
 * @Version 1
 */
public class BindingConfig {

    public final SparseArray<Object> params = new SparseArray<>();

    public BindingConfig() {
    }

    public BindingConfig add(Integer id, Object param) {
        if (params.get(id) == null) {
            params.put(id, param);
        }
        return this;
    }
}
