package com.cdtde.chongdetang.utils.binding

import androidx.databinding.BindingAdapter
import com.drake.statelayout.StateLayout

/**
 *
 *@Author John
 */
object StateAdapter {

    @JvmStatic
    @BindingAdapter("state_isEmpty", "state_tip")
    fun StateLayout.listen(isEmpty: Boolean, tip: String?) {
        if (isEmpty) {
            showEmpty(tip)
        } else {
            showContent()
        }
    }
}