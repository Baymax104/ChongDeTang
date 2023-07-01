package com.cdtde.chongdetang.base.vm

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.cdtde.chongdetang.base.view.BaseActivity
import com.cdtde.chongdetang.base.view.BaseFragment
import java.util.*

/**
 * 依赖注入工具类，使用IoC原理.
 *@author John
 */
object Injector {

    /**
     * 对[BaseActivity]子类中的ViewModel组件进行依赖注入.
     * @param B [ViewDataBinding]子类类型
     * @param activity [BaseActivity]子类对象
     * @param viewModelScope [ActivityViewModelScope]对象
     */
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

    /**
     * 对[BaseFragment]子类中的ViewModel组件进行依赖注入.
     * @param B [ViewDataBinding]子类类型
     * @param fragment [BaseFragment]子类对象
     * @param viewModelScope [FragmentViewModelScope]对象
     */
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