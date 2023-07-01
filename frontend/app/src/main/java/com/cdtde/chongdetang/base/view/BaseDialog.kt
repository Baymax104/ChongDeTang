package com.cdtde.chongdetang.base.view

import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.lxj.xpopup.core.BasePopupView

/**
 * 基于 XPopup 库构建对话框的视图绑定工具类.
 * @author John
 */
object DialogBinder {

    /**
     * 绑定方法.
     * @param dialog 对话框实例对象
     * @param config 视图绑定参数 [BindingConfig] ，默认为 null
     */
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