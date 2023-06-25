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
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/8 19:12
 *@Version 1
 */
object ApplicationViewModelStore : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()
}

open class ApplicationViewModelScope {
    private var applicationProvider = ViewModelProvider(ApplicationViewModelStore)
    fun <T : ViewModel> getFromApplication(cl: Class<T>) = applicationProvider[cl]
}

open class ActivityViewModelScope(activity: AppCompatActivity) : ApplicationViewModelScope() {
    constructor(activity: Activity) : this(activity as AppCompatActivity)

    private val activityProvider = ViewModelProvider(activity)
    fun <T : ViewModel> getFromActivity(cl: Class<T>) = activityProvider[cl]
}

class FragmentViewModelScope(fragment: Fragment) :
    ActivityViewModelScope(fragment.requireActivity()) {
    private val fragmentProvider = ViewModelProvider(fragment)
    fun <T : ViewModel> getFromFragment(cl: Class<T>) = fragmentProvider[cl]
}

object DialogScope {
    @MainThread
    @JvmStatic
    fun <VM : ViewModel> getFromActivity(dialog: BasePopupView, cl: Class<VM>): VM {
        val activity = XPopupUtils.context2Activity(dialog.context) as ComponentActivity
        return ViewModelProvider(activity)[cl]
    }

    @MainThread
    @JvmStatic
    fun <VM : ViewModel> getFromApplication(cl: Class<VM>): VM {
        return ViewModelProvider(ApplicationViewModelStore)[cl]
    }
}

