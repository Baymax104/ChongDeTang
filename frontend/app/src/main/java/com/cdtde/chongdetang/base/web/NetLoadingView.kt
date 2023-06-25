package com.cdtde.chongdetang.base.web

import android.content.Context
import com.lxj.xpopup.impl.LoadingPopupView

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/20 11:06
 *@Version 1
 */
class NetLoadingView(context: Context) : LoadingPopupView(context, 0), NetLifeCycleObserver {

    override fun onStart() {
        show()
    }

    override fun onFinish() {
        smartDismiss()
    }

}