package com.cdtde.chongdetang.base.vm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.cdtde.chongdetang.base.web.NetLifeCycle
import com.cdtde.chongdetang.base.web.NetLifeCycleObserver
import io.reactivex.functions.Consumer

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/8 19:10
 *@Version 1
 */
abstract class Requester : ViewModel(), NetLifeCycle {

    data class ReqCallback<E>(
        @JvmField val onSuccess: Consumer<E>,
        @JvmField val onFail: Consumer<String>,
        @JvmField val lifeCycle: NetLifeCycle
    )

    private val observers: MutableList<NetLifeCycleObserver> = mutableListOf()

    fun registerObserver(observer: NetLifeCycleObserver, lifecycleOwner: LifecycleOwner) {
        observers.add(observer)
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                unregisterObserver(observer)
            }
        })
    }

    private fun unregisterObserver(observer: NetLifeCycleObserver) {
        observers.remove(observer)
    }

    override fun onStart() {
        observers.forEach { it.onStart() }
    }

    override fun onFinish() {
        observers.forEach { it.onFinish() }
    }
}
