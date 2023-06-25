package com.cdtde.chongdetang.utils.binding

import android.widget.CheckBox
import androidx.databinding.BindingAdapter

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/21 21:56
 *@Version 1
 */
object CheckBoxAdapter {

    interface OnCheckBoxClick {
        fun onClick(isChecked: Boolean)
    }

    @JvmStatic
    @BindingAdapter("checkBox_list", "checkBox_selected")
    fun <E : Any> CheckBox.allSelect(list: List<E>, selected: Int) {
        isChecked = list.isNotEmpty() && list.size == selected
    }

    @JvmStatic
    @BindingAdapter("checkBox_onClick")
    fun CheckBox.onClick(onCheckBoxClick: OnCheckBoxClick) {
        setOnClickListener { onCheckBoxClick.onClick((it as CheckBox).isChecked) }
    }
}