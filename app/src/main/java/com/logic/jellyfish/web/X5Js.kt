package com.logic.jellyfish.web

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import com.logic.jellyfish.ui.test.MainActivity
import com.logic.jellyfish.utils.ext.toast

class X5Js(private val context: Context) {

   @JavascriptInterface
   fun navigateToMainActivity() {
      context.toast("你好呀")
      val intent = Intent(context, MainActivity::class.java)
      context.startActivity(intent)
   }
}