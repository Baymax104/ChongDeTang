package com.cdtde.chongdetang.utils

import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.cdtde.chongdetang.R
import com.drake.statusbar.immersive
import com.drake.statusbar.statusPadding
import com.google.android.material.appbar.AppBarLayout

/**
 * 初始化Activity、Fragment的ToolBar工具类.
 */
object WindowUtil {
    /**
     * 初始化Activity的ToolBar.
     *
     * 隐藏原标题，设置状态栏主题，ToolBar背景延伸到状态栏
     * @param toolbar    Activity的ToolBar
     * @param back       是否需要返回按钮
     * @param lightTheme 是否是亮色主题(白底黑字)，默认为暗色主题(黑底白字)
     * @param extent     将状态栏的颜色自动填充为该view的背景色
     */
    @JvmOverloads
    @JvmStatic
    fun initActivityWindow(
        activity: AppCompatActivity,
        toolbar: Toolbar,
        extent: View? = null,
        back: Boolean = true,
        lightTheme: Boolean = false
    ) {
        with(activity) {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(back)
                if (back) {
                    setHomeAsUpIndicator(R.drawable.arrow_left)
                }
            }
            if (extent != null) {
                immersive(extent, darkMode = !lightTheme)
            } else {
                immersive(darkMode = !lightTheme)
            }
        }
    }

    /**
     * 初始化主页面fragment的ToolBar，实现fitsSystemWindows.
     *
     * 当含有多个子fragment时，第一个接收到fitsSystemWindows的fragment会消费事件，不再分发给其他fragment
     * 手动设置paddingTop，实现fitsSystemWindows效果
     * @param toolbarLayout 布局中包含ToolBar的AppBarLayout
     */
    @JvmStatic
    fun initWindowPadding(toolbarLayout: AppBarLayout) {
        toolbarLayout.statusPadding()
    }
}
