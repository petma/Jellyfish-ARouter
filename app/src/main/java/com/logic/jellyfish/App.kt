package com.logic.jellyfish

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.chat.ChatApp
import com.logic.utils.Cache
import com.logic.web.WebApp
import me.jessyan.autosize.AutoSizeConfig

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    app = this
    Cache.init(this)
    AutoSizeConfig.getInstance().isCustomFragment = true

    initChildApp()
    initARouter()
  }

  private fun initChildApp() {
    WebApp.onCreate(this)
    ChatApp.onCreate(this)
  }

  private fun initARouter() {
    if (BuildConfig.DEBUG) {
      ARouter.openDebug()
      ARouter.openLog()
    }
    ARouter.init(this)
  }

  companion object {
    lateinit var app: App private set
  }

}
