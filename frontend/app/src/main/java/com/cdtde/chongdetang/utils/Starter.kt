package com.cdtde.chongdetang.utils

import android.content.Context
import android.content.Intent

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/10 17:27
 * @Version 1
 */
object Starter {
    @JvmStatic
    fun actionStart(context: Context, cl: Class<*>) {
        context.startActivity(Intent(context, cl))
    }
}


