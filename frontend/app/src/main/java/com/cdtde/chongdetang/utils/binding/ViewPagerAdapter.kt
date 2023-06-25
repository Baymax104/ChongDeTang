package com.cdtde.chongdetang.utils.binding

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cdtde.chongdetang.adapter.FragmentAdapter

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/16 18:11
 *@Version 1
 */
object ViewPagerAdapter {

    fun interface PageChangeCallback {
        fun onPageChange(page: Int)
    }

    @JvmStatic
    @BindingAdapter("viewPager_adapter", "viewPager_fragments")
    fun ViewPager2.fragments(adapter: FragmentAdapter, fragments: List<Fragment>) {
        adapter.setFragments(fragments)
        this.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("viewPager_page", "viewPager_smooth")
    fun ViewPager2.onPageChange(page: Int, smooth: Boolean) {
        setCurrentItem(page, smooth)
    }

    @JvmStatic
    @BindingAdapter("viewPager_onPageChange")
    fun ViewPager2.registerPageChangeCallback(callback: PageChangeCallback) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                callback.onPageChange(position)
            }
        })
    }
}