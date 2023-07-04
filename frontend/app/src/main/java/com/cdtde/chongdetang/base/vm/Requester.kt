package com.cdtde.chongdetang.base.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.cdtde.chongdetang.base.web.NetLifeCycle
import com.cdtde.chongdetang.base.web.NetLifeCycleObserver
import io.reactivex.functions.Consumer
import java.net.SocketTimeoutException

/**
 * ViewModel组件之一，负责页面的数据请求.
 * 实现了[NetLifeCycle]接口，在网络请求开始时调用所有观察者的[NetLifeCycleObserver.onStart]方法，
 * 在网络请求结束时调用所有观察者的[NetLifeCycleObserver.onFinish]方法
 * @author John
 */
abstract class Requester : ViewModel(), NetLifeCycle {

    /**
     * 网络请求回调，封装了页面设置的回调以及[NetLifeCycle]对象，用于Repository层控制网络周期进行.
     * @param E 请求成功回调的数据类型
     * @property onSuccess 请求成功回调
     * @property onFail 请求失败回调
     * @property lifeCycle 网络生命周期对象，实现[NetLifeCycle]接口
     */
    data class ReqCallback<E>(
        @JvmField val onSuccess: Consumer<E>,
        @JvmField val onFail: Consumer<String>,
        @JvmField val lifeCycle: NetLifeCycle
    ) {
        fun baseHandleException(throwable: Throwable) {
            when (throwable) {
                is SocketTimeoutException -> "网络出了点小问题~"
                else -> throwable.message
            }?.let { onFail.accept(it) }
        }
    }

    private val observers: MutableList<NetLifeCycleObserver> = mutableListOf()

    /**
     * 注册网络周期观察者.
     * @param observer 观察者，实现了[NetLifeCycleObserver]
     * @param lifecycleOwner 观察者的生命周期
     */
    fun registerObserver(observer: NetLifeCycleObserver, lifecycleOwner: LifecycleOwner) {
        observers.add(observer)
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                unregisterObserver(observer)
            }
        })
    }

    /**
     * 取消观察者注册.
     * @param observer 取消注册的观察者对象
     */
    private fun unregisterObserver(observer: NetLifeCycleObserver) {
        observers.remove(observer)
    }

    /**
     * 重写[NetLifeCycle.onStart]方法，调用所有观察者的[NetLifeCycleObserver.onStart]方法.
     */
    override fun onStart() {
        observers.forEach { it.onStart() }
    }

    /**
     * 重写[NetLifeCycle.onFinish]方法，调用所有观察者的[NetLifeCycleObserver.onFinish]方法.
     */
    override fun onFinish() {
        observers.forEach { it.onFinish() }
    }
}
