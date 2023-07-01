package com.cdtde.chongdetang.utils

import android.content.Context
import android.content.Intent

/**
 * Activity启动器
 */
object Starter {

    /**
     * 启动Activity
     * @param context 当前页面Context
     * @param cl 目的Activity的Class对象
     */
    @JvmStatic
    fun actionStart(context: Context, cl: Class<*>) {
        context.startActivity(Intent(context, cl))
    }
}


