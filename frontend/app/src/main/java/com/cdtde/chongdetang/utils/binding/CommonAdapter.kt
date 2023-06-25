package com.cdtde.chongdetang.utils.binding

import android.view.View
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.databinding.BindingAdapter
import com.cdtde.chongdetang.R

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/16 23:22
 *@Version 1
 */
object CommonAdapter {

    @JvmStatic
    @BindingAdapter("web_url")
    fun WebView.url(url: String) {
        loadUrl(url)
    }

    @JvmStatic
    fun bindPwdEdit(view: View, edit: EditText) {
        view as ImageView
        val type = if (edit.inputType == 128) 129 else 128
        val img = if (edit.inputType == 128) R.drawable.visible else R.drawable.invisible
        edit.inputType = type
        view.setImageResource(img)
        edit.setSelection(edit.text.length)
    }

    @JvmStatic
    @BindingAdapter("toolbar_onMenuItemClick")
    fun Toolbar.onMenuClick(listener: OnMenuItemClickListener) {
        setOnMenuItemClickListener(listener)
    }
}