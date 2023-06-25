package com.cdtde.chongdetang.base.view

import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.lxj.xpopup.core.BasePopupView

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/4/3 18:42
 *@Version 1
 */

object DialogBinder {

    @JvmOverloads
    @JvmStatic
    fun bind(dialog: BasePopupView, config: BindingConfig? = null) {
        // binding
        val binding: ViewDataBinding = DataBindingUtil.bind(dialog.popupImplView) ?: return
        binding.lifecycleOwner = dialog

        config?.params?.forEach { key, value -> binding.setVariable(key, value) }

        // register unbind
        dialog.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                binding.unbind()
            }
        })
    }
}