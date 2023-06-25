package com.cdtde.chongdetang.base.vm

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/8 18:53
 *@Version 1
 */
abstract class StateHolder : ViewModel()

class State<E>(@NonNull value: E) : UnPeekLiveData<E>(value) {

    @NonNull
    @MainThread
    override fun getValue(): E =
        super.getValue() ?: throw NullPointerException("state value is null")

    @MainThread
    override fun setValue(@NonNull value: E) {
        super.setValue(value)
    }
}
