package com.cdtde.chongdetang.base.vm

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.cdtde.chongdetang.base.view.BaseActivity
import com.cdtde.chongdetang.base.view.BaseFragment
import java.util.*

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/23 17:52
 *@Version 1
 */
object Injector {

    @JvmStatic
    fun <B : ViewDataBinding> inject(
        activity: BaseActivity<B>,
        viewModelScope: ActivityViewModelScope
    ) {
        val cl: Class<*> = activity.javaClass
        val fields = cl.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(InjectScope::class.java) &&
                ViewModel::class.java.isAssignableFrom(field.type)
            ) {
                val typeClass = field.type.asSubclass(ViewModel::class.java)
                field.isAccessible = true

                val annotation = field.getAnnotation(InjectScope::class.java)
                val scope: Scopes = Objects.requireNonNull(annotation).value
                try {
                    field[activity] = when (scope) {
                        Scopes.APPLICATION -> viewModelScope.getFromApplication(typeClass)
                        Scopes.ACTIVITY -> viewModelScope.getFromActivity(typeClass)
                        Scopes.FRAGMENT -> throw IllegalArgumentException("Activity cannot attain Fragment Scope")
                    }
                } catch (e: IllegalAccessException) {
                    LogUtils.eTag("cdt-inject", e.message)
                }
            }
        }
    }

    @JvmStatic
    fun <B : ViewDataBinding> inject(
        fragment: BaseFragment<B>,
        viewModelScope: FragmentViewModelScope
    ) {

        val cl: Class<*> = fragment.javaClass
        val fields = cl.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(InjectScope::class.java) &&
                ViewModel::class.java.isAssignableFrom(field.type)
            ) {
                val typeClass = field.type.asSubclass(ViewModel::class.java)
                field.isAccessible = true

                val annotation = field.getAnnotation(InjectScope::class.java)
                val scope: Scopes = Objects.requireNonNull(annotation).value
                try {
                    field[fragment] = when (scope) {
                        Scopes.APPLICATION -> viewModelScope.getFromApplication(typeClass)
                        Scopes.ACTIVITY -> viewModelScope.getFromActivity(typeClass)
                        Scopes.FRAGMENT -> viewModelScope.getFromFragment(typeClass)
                    }
                } catch (e: IllegalAccessException) {
                    LogUtils.eTag("cdt-inject", e.message)
                }
            }
        }
    }
}