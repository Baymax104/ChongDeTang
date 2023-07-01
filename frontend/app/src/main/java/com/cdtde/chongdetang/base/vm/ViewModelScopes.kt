package com.cdtde.chongdetang.base.vm

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.util.XPopupUtils

/**
 * 全局Application作用域ViewModel存储对象.
 */
object ApplicationViewModelStore : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()
}

/**
 * Application作用域ViewModel获取类.
 */
open class ApplicationViewModelScope {
    private var applicationProvider = ViewModelProvider(ApplicationViewModelStore)

    /**
     * 从Application作用域中获取ViewModel对象.
     * @param T ViewModel组件类型
     * @param cl ViewModel组件Class对象
     * @return ViewModel组件对象
     */
    fun <T : ViewModel> getFromApplication(cl: Class<T>) = applicationProvider[cl]
}

/**
 * Activity作用域ViewModel获取类.
 * @constructor 根据构造参数创建[ViewModelProvider]
 * @param activity Activity
 */
open class ActivityViewModelScope(activity: AppCompatActivity) : ApplicationViewModelScope() {
    constructor(activity: Activity) : this(activity as AppCompatActivity)

    private val activityProvider = ViewModelProvider(activity)

    /**
     * 从Activity作用域中获取ViewModel对象.
     * @param T ViewModel组件类型
     * @param cl ViewModel组件Class对象
     * @return ViewModel组件对象
     */
    fun <T : ViewModel> getFromActivity(cl: Class<T>) = activityProvider[cl]
}

/**
 * Fragment作用域ViewModel获取类.
 * @constructor 根据构造参数创建[ViewModelProvider]
 * @param fragment Fragment
 */
class FragmentViewModelScope(fragment: Fragment) :
    ActivityViewModelScope(fragment.requireActivity()) {
    private val fragmentProvider = ViewModelProvider(fragment)

    /**
     * 从Fragment作用域中获取ViewModel对象.
     * @param T ViewModel组件类型
     * @param cl ViewModel组件Class对象
     * @return ViewModel组件对象
     */
    fun <T : ViewModel> getFromFragment(cl: Class<T>) = fragmentProvider[cl]
}

/**
 * 基于XPopup库对话框的ViewModel获取对象.
 * 支持Activity作用域和Application作用域
 */
object DialogScope {

    /**
     * 从Activity作用域获取ViewModel组件.
     * @param VM ViewModel组件类型
     * @param dialog XPopup对话框实例对象
     * @param cl ViewModel组件Class对象
     * @return ViewModel组件对象
     */
    @MainThread
    @JvmStatic
    fun <VM : ViewModel> getFromActivity(dialog: BasePopupView, cl: Class<VM>): VM {
        val activity = XPopupUtils.context2Activity(dialog.context) as ComponentActivity
        return ViewModelProvider(activity)[cl]
    }

    /**
     * 从Application作用域获取ViewModel组件.
     * @param VM ViewModel组件类型
     * @param cl ViewModel组件Class对象
     * @return ViewModel组件对象
     */
    @MainThread
    @JvmStatic
    fun <VM : ViewModel> getFromApplication(cl: Class<VM>): VM {
        return ViewModelProvider(ApplicationViewModelStore)[cl]
    }
}

