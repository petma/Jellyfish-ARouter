package com.logic.jellyfish.utils

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import com.logic.jellyfish.ui.ready.ReadyActivity

class X5Javascript(private val context: Context) {

  @JavascriptInterface
  fun navigateToMainActivity() {
    val intent = Intent(context, ReadyActivity::class.java)
    context.startActivity(intent)
  }
}