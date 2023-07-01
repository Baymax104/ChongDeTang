package com.cdtde.chongdetang.base.vm

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * ViewModel组件之一，负责页面数据状态存储.
 * @author John
 */
abstract class StateHolder : ViewModel()

/**
 * 存储页面数据，继承[UnPeekLiveData]，支持DataBinding.
 * @param E 数据类型
 * @constructor
 * [UnPeekLiveData]构造
 * @param value 页面数据初值
 */
class State<E>(@NonNull value: E) : UnPeekLiveData<E>(value) {

    /**
     * 获取数据原值.
     * @return 数据原值，必须为非空
     */
    @NonNull
    @MainThread
    override fun getValue(): E =
        super.getValue() ?: throw NullPointerException("state value is null")

    /**
     * 设置数据值.
     * @param value 设置值，必须为非空
     */
    @MainThread
    override fun setValue(@NonNull value: E) {
        super.setValue(value)
    }
}
