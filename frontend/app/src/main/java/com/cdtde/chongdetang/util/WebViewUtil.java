package com.cdtde.chongdetang.util;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 23:18
 * @Version 1
 */
public class WebViewUtil {

    /**
     * 配置WebView的HTML页面，删除其中的部分元素
     * @param view WebView
     * @param isListPage html页面是否是列表页面
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static void configure(WebView view, boolean isListPage) {
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 90) {
                    view.loadUrl("javascript:(function() {" +
                            "document.getElementsByClassName('nav')[0].remove();" +
                            "document.getElementsByClassName('logo')[0].remove();" +
                            "document.getElementById('bottom').remove();" +
                            (isListPage ? "document.getElementsByClassName('flc')[0].remove();" : "") +
                            "})();");
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }
}
