package com.cdtde.chongdetang.base.web

/**
 * 网络生命周期观察者接口，具有Start和Finish两个周期
 */
interface NetLifeCycleObserver {

    fun onStart()

    fun onFinish()
}