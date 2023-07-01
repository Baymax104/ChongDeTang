package com.cdtde.chongdetang.base.vm

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.jeremyliao.liveeventbus.core.LiveEventBusCore

/**
 * ViewModel组件之一，负责页面之间的数据通信.
 */
abstract class MessageHolder : ViewModel() {

    protected val bus = LiveEventBusCore.get()!!
    inline fun <reified T> LiveEventBusCore.with(key: String) = with(key, T::class.java)!!

    /**
     * 普通事件封装类.
     * @param E 传输数据类型
     * @property value 传输数据
     */
    data class EventObject<E>(val value: E)

    /**
     * 身份识别事件封装类.
     * @param E 传输数据类型
     * @property value 传输数据
     * @property key 身份key
     */
    data class EventObjectWithKey<E>(val value: E, val key: String)

    /**
     * 普通事件回调接口.
     * @param T 传输数据类型
     */
    interface ObserverCallback<T> {
        fun onChange(value: T)
    }

    /**
     * 身份识别事件回调接口.
     * @param T 传输数据类型
     */
    interface ObserverCallbackWithKey<T> {
        fun onChange(value: T, key: String)
    }


    /**
     * 表示一个广播事件，各个页面通过订阅和发送事件来传输数据.
     * @param S 发送数据类型
     * @param R 接收数据类型
     */
    inner class Event<S, R> {
        private val sender = bus.with<EventObject<S>>("sender-${super.hashCode()}")
        private val replier = bus.with<EventObject<R>>("replier-${super.hashCode()}")

        /**
         * 发送数据方法.
         * @param value 发送数据
         */
        @MainThread
        fun send(@NonNull value: S) = apply { sender.post(EventObject(value)) }

        /**
         * 回复数据方法.
         * @see send
         */
        @MainThread
        fun reply(@NonNull value: R) = apply { replier.post(EventObject(value)) }

        /**
         * 订阅事件发送.
         * @param owner 订阅的声明周期
         * @param sticky 是否是粘性事件
         * @param callback 事件回调
         */
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

        /**
         * 订阅事件回复.
         * @see observeSend
         */
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

    /**
     * 表示一个具有身份识别的事件.
     * 对于多个接收者，通过key可以指定其中一个接收者接收数据
     * @param S 发送数据类型
     * @param R 接受数据类型
     */
    inner class EventWithKey<S, R> {

        private val sender = bus.with<EventObjectWithKey<S>>("key-sender-${super.hashCode()}")
        private val replier = bus.with<EventObjectWithKey<R>>("key-replier-${super.hashCode()}")


        /**
         * 发送数据.
         * @param key 身份key
         * @see Event.send
         */
        @MainThread
        fun send(@NonNull value: S, key: String) = apply {
            sender.post(EventObjectWithKey(value, key))
        }

        /**
         * 回复数据.
         * @param key 身份key
         * @see Event.reply
         */
        @MainThread
        fun reply(@NonNull value: R, key: String) = apply {
            replier.post(EventObjectWithKey(value, key))
        }

        /**
         * 订阅发送事件.
         * @param key 身份识别key
         * @see Event.observeSend
         */
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

        /**
         * 订阅回复事件.
         * @param key 身份识别key
         * @see Event.observeReply
         */
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
