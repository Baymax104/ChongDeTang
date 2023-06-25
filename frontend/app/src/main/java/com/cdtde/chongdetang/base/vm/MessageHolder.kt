package com.cdtde.chongdetang.base.vm

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jeremyliao.liveeventbus.core.LiveEventBusCore

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/8 19:06
 *@Version 1
 */
abstract class MessageHolder : ViewModel() {

    protected val bus = LiveEventBusCore.get()!!
    inline fun <reified T> LiveEventBusCore.with(key: String) = with(key, T::class.java)!!

    data class EventObject<E>(val value: E)
    data class EventObjectWithKey<E>(val value: E, val key: String)

    interface ObserverCallback<T> {
        fun onChange(value: T)
    }

    interface ObserverCallbackWithKey<T> {
        fun onChange(value: T, key: String)
    }


    inner class Event<S, R> {
        private val sender = bus.with<EventObject<S>>("sender-${super.hashCode()}")
        private val replier = bus.with<EventObject<R>>("replier-${super.hashCode()}")

        @MainThread
        fun send(@NonNull value: S) = apply { sender.post(EventObject(value)) }

        @MainThread
        fun reply(@NonNull value: R) = apply { replier.post(EventObject(value)) }

        @MainThread
        @JvmOverloads
        fun observeSend(
            owner: LifecycleOwner,
            sticky: Boolean = false,
            callback: ObserverCallback<S>
        ) {
            val observer: Observer<EventObject<S>> = Observer { callback.onChange(it.value) }
            if (sticky) {
                sender.observeSticky(owner, observer)
            } else {
                sender.observe(owner, observer)
            }
        }

        @MainThread
        @JvmOverloads
        fun observeReply(
            owner: LifecycleOwner,
            sticky: Boolean = false,
            callback: ObserverCallback<R>
        ) {
            val observer: Observer<EventObject<R>> = Observer { callback.onChange(it.value) }
            if (sticky) {
                replier.observeSticky(owner, observer)
            } else {
                replier.observe(owner, observer)
            }
        }
    }

    inner class EventWithKey<S, R> {

        private val sender = bus.with<EventObjectWithKey<S>>("key-sender-${super.hashCode()}")
        private val replier = bus.with<EventObjectWithKey<R>>("key-replier-${super.hashCode()}")


        @MainThread
        fun send(@NonNull value: S, key: String) = apply {
            sender.post(EventObjectWithKey(value, key))
        }

        @MainThread
        fun reply(@NonNull value: R, key: String) = apply {
            replier.post(EventObjectWithKey(value, key))
        }

        @MainThread
        @JvmOverloads
        fun observeSend(
            owner: LifecycleOwner,
            sticky: Boolean = false,
            key: String,
            callback: ObserverCallbackWithKey<S>
        ) {
            val observer: Observer<EventObjectWithKey<S>> = Observer {
                if (key == "./") {  // 接收任何消息，需要key

                    callback.onChange(it.value, it.key)
                } else {  // 定向接收，需要key
                    if (key == it.key) {
                        callback.onChange(it.value, it.key)
                    }
                }
            }
            if (sticky) {
                sender.observeSticky(owner, observer)
            } else {
                sender.observe(owner, observer)
            }
        }

        @MainThread
        @JvmOverloads
        fun observeReply(
            owner: LifecycleOwner,
            sticky: Boolean = false,
            key: String,
            callback: ObserverCallbackWithKey<R>
        ) {
            val observer: Observer<EventObjectWithKey<R>> = Observer {
                if (key == "./") {  // 接收任何消息，需要key
                    callback.onChange(it.value, it.key)
                } else {  // 定向接收，需要key
                    if (key == it.key) {
                        callback.onChange(it.value, it.key)
                    }
                }
            }
            if (sticky) {
                replier.observeSticky(owner, observer)
            } else {
                replier.observe(owner, observer)
            }
        }
    }

}
