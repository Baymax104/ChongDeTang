package com.cdtde.chongdetang.utils

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/1/13 23:18
 * @Version 1
 */


object WebViewUtil {

    /**
     * 配置WebView的HTML页面，删除其中的部分元素
     * @param isListPage html页面是否是列表页面
     */
    @SuppressLint("SetJavaScriptEnabled")
    @JvmStatic
    fun configure(webView: WebView, isListPage: Boolean) {
        with(webView) {
            with(settings) {
                javaScriptEnabled = true
                domStorageEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (request?.url != null) {
                        view?.loadUrl(request.url.toString())
                    }
                    return true
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress >= 90 && view != null) {
                        view.loadUrl(
                            """
                        javascript:(function() {
                            document.getElementsByClassName('nav')[0].remove();
                            document.getElementsByClassName('logo')[0].remove();
                            document.getElementById('bottom').remove();
                            ${if (isListPage) "document.getElementsByClassName('flc')[0].remove();" else ""}
                        })();
                    """.trimIndent()
                        )
                    }
                    super.onProgressChanged(view, newProgress)
                }
            }
        }
    }
}
