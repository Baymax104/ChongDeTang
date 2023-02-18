package com.cdtde.chongdetang.util;

import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cdtde.chongdetang.R;
import com.google.android.material.appbar.AppBarLayout;

/**
 * @Description 初始化Activity、Fragment的ToolBar工具类
 * @Author John
 * @email
 * @Date 2023/1/12 16:35
 * @Version 1
 */
public class WindowUtil {

    /**
     * 初始化Activity的ToolBar
     * 隐藏原标题，设置状态栏主题，ToolBar背景延伸到状态栏
     *
     * @param toolbar    Activity的ToolBar
     * @param activity   activity实例
     * @param back       是否需要返回按钮
     * @param lightTheme 是否是亮色主题(白底黑字)，默认为暗色主题(黑底白字)
     */
    public static void initActivityWindow(Toolbar toolbar, AppCompatActivity activity, boolean back, boolean lightTheme) {
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(back);
            if (back) {
                actionBar.setHomeAsUpIndicator(R.drawable.arrow_left);
            }
        }
        Window window = activity.getWindow();
        if (!lightTheme) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        int theme = lightTheme ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0;
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|theme);
    }

    /**
     * 初始化主页面fragment的ToolBar，实现fitsSystemWindows
     * 当含有多个子fragment时，第一个接收到fitsSystemWindows的fragment会消费事件，不再分发给其他fragment
     * 手动设置paddingTop，实现fitsSystemWindows效果
     * @param toolbarLayout 包含ToolBar的AppBarLayout
     * @param fragment fragment
     */
    public static void initFragmentWindow(AppBarLayout toolbarLayout, Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) fragment.requireActivity();
        View decorView = activity.getWindow().getDecorView();
        WindowInsets insets = decorView.getRootWindowInsets();
        toolbarLayout.setPadding(0, insets.getSystemWindowInsetTop(), 0, 0);
    }
}
