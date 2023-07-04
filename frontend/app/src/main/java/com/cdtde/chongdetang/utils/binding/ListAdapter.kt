package com.cdtde.chongdetang.utils.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.cdtde.chongdetang.adapter.BannerAdapter
import com.cdtde.chongdetang.adapter.SearchTagAdapter
import com.cdtde.chongdetang.base.view.BaseAdapter
import com.youth.banner.Banner
import com.zhy.view.flowlayout.TagFlowLayout
import java.util.function.Consumer

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
        val adapter = (this.adapter as? SearchTagAdapter) ?: SearchTagAdapter(data)
        adapter.setData(data)
        this.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("tag_onClick")
    fun TagFlowLayout.bind(consumer: Consumer<String>) {
        setOnTagClickListener { _, position, _ ->
            val tag = adapter.getItem(position) as String
            consumer.accept(tag)
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
    @BindingAdapter("recycler_hasDivider")
    fun RecyclerView.divider(boolean: Boolean) {
        if (boolean) {
            addItemDecoration(DividerItemDecoration(Utils.getApp(), DividerItemDecoration.VERTICAL))
        }
    }

    @JvmStatic
    @BindingAdapter("banner_data")
    fun Banner<Int, BannerAdapter>.set(data: List<Int>) {
        if (this.adapter == null) {
            setAdapter(BannerAdapter(data))
        }
    }

}