package com.logic.jellyfish

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.utils.Cache
import com.logic.web.WebApp
import me.jessyan.autosize.AutoSizeConfig

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    app = this
    Cache.init(this)
    WebApp.onCreate(this)
    AutoSizeConfig.getInstance().isCustomFragment = true
    initARouter()
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
