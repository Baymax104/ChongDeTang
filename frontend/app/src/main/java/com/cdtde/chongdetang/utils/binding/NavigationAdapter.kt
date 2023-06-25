package com.cdtde.chongdetang.utils.binding

import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

/**
 *@Description
 *@Author John
 *@email
 *@Date 2023/6/16 18:07
 *@Version 1
 */
object NavigationAdapter {

    @JvmStatic
    @BindingAdapter("bottomNavigation_onItemSelected")
    fun BottomNavigationView.onItemSelected(listener: NavigationBarView.OnItemSelectedListener) {
        setOnItemSelectedListener(listener)
    }

    @JvmStatic
    @BindingAdapter("bottomNavigation_page")
    fun BottomNavigationView.onPageChange(page: Int) {
        menu[page].isChecked = true
    }

}