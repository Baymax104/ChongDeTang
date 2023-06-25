package com.cdtde.chongdetang.utils.binding

import androidx.databinding.BindingAdapter
import com.mcxtzhang.swipemenulib.SwipeMenuLayout

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/22 14:54
 *@Version 1
 */
object SwipeMenuAdapter {

    @JvmStatic
    @BindingAdapter("swipeMenu_enable")
    fun SwipeMenuLayout.swipeEnable(enabled: Boolean) {
        isSwipeEnable = enabled
    }
}