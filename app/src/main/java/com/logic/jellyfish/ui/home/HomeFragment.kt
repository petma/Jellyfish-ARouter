package com.logic.jellyfish.ui.home

import com.logic.jellyfish.R
import com.logic.jellyfish.base.WebFragment
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : WebFragment(R.layout.fragment_home) {

  override fun setWebView(): WebView {
    return web_view
  }

  override fun init() {
    webView.loadUrl("https://m.weibo.cn/")
  }

}
