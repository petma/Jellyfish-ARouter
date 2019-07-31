package com.logic.jellyfish.view

import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings

import com.tencent.smtt.sdk.WebView

class MyWebView : WebView {

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    init()
  }

  constructor(context: Context, attributeSet: AttributeSet, i: Int) : super(
    context,
    attributeSet,
    i
  ) {
    init()
  }

  private fun init() {
    val webSettings = settings
    webSettings.apply {
      javaScriptEnabled = true
      javaScriptCanOpenWindowsAutomatically = true
      allowFileAccess = true
      layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
      useWideViewPort = true
      setSupportMultipleWindows(true)

      setAppCacheEnabled(true)
      // setDatabaseEnabled(true)
      domStorageEnabled = true
      setGeolocationEnabled(true)
      setAppCacheMaxSize(Long.MAX_VALUE)
      // setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
      cacheMode = WebSettings.LOAD_NO_CACHE

    }

  }

}
