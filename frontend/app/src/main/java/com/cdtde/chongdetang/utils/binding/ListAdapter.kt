package com.cdtde.chongdetang.utils.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cdtde.chongdetang.adapter.BannerAdapter
import com.cdtde.chongdetang.adapter.SearchTagAdapter
import com.cdtde.chongdetang.base.view.BaseAdapter
import com.youth.banner.Banner
import com.zhy.view.flowlayout.TagFlowLayout

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/16 18:44
 *@Version 1
 */
object ListAdapter {

    @JvmStatic
    @BindingAdapter("tag_data")
    fun TagFlowLayout.adapter(data: List<String>) {
        val adapter = (this.adapter as? SearchTagAdapter) ?: SearchTagAdapter(
            data
        )
        adapter.setData(data)
        this.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("tag_bind")
    fun TagFlowLayout.bind(editText: EditText) {
        setOnTagClickListener { _, position, _ ->
            editText.setText(this.adapter.getItem(position) as? String)
            return@setOnTagClickListener true
        }
    }

    @JvmStatic
    @BindingAdapter("recycler_adapter", "recycler_data")
    fun <T, B : ViewDataBinding> RecyclerView.adapter(adapter: BaseAdapter<T, B>, data: List<T>) {
        adapter.setList(data)
        this.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("banner_data")
    fun Banner<Int, BannerAdapter>.set(data: List<Int>) {
        if (this.adapter == null) {
            setAdapter(BannerAdapter(data))
        }
    }

}