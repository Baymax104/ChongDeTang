package com.cdtde.chongdetang.utils.binding

import android.text.InputFilter
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputEditText

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/23 0:01
 *@Version 1
 */
object EditAdapter {

    @JvmStatic
    @BindingAdapter("edit_input")
    fun TextInputEditText.input(string: String) {
        setText(string)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "edit_input", event = "edit_inputAttrChanged")
    fun TextInputEditText.inverseInput() = text?.toString() ?: ""

    @JvmStatic
    @BindingAdapter("edit_inputAttrChanged")
    fun TextInputEditText.inputInverseListener(attrChanged: InverseBindingListener) {
        addTextChangedListener { attrChanged.onChange() }
    }

    @JvmStatic
    @BindingAdapter("edit_text")
    fun EditText.text(string: String) {
        setText(string)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "edit_text", event = "edit_textAttrChanged")
    fun EditText.inverseText() = text?.toString() ?: ""

    @JvmStatic
    @BindingAdapter("edit_textAttrChanged")
    fun EditText.inverseListener(attrChanged: InverseBindingListener) {
        addTextChangedListener { attrChanged.onChange() }
    }

    @JvmStatic
    @BindingAdapter("edit_length")
    fun EditText.filterLength(num: Int) {
        filters = arrayOf(InputFilter.LengthFilter(num))
    }

    @JvmStatic
    @BindingAdapter("edit_length")
    fun TextInputEditText.filterLength(num: Int) {
        filters = arrayOf(InputFilter.LengthFilter(num))
    }

}