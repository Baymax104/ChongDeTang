package com.cdtde.chongdetang.base.web

import android.content.Context
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * 可观察网络生命周期的加载对话框.
 * 实现了[NetLifeCycleObserver]接口，在网络请求开始时展示，在请求结束后关闭
 * @constructor
 * 继承[LoadingPopupView]创建
 * @param context Context
 */
class NetLoadingView(context: Context) : LoadingPopupView(context, 0), NetLifeCycleObserver {

    override fun onStart() {
        show()
    }

    override fun onFinish() {
        smartDismiss()
    }

}