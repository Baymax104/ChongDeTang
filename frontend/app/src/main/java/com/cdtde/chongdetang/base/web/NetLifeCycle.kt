package com.cdtde.chongdetang.base.web

/**
 * 网络生命周期接口，具有Start和Finish两个周期
 */
interface NetLifeCycle {

    fun onStart()

    fun onFinish()
}