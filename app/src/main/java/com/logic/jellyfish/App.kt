package com.logic.jellyfish

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.utils.Cache
import com.logic.web.WebApp
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.util.NIMUtil
import me.jessyan.autosize.AutoSizeConfig

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    NIMClient.init(this, null, null)

    if (NIMUtil.isMainProcess(this)) {
      app = this
      Cache.init(this)
      AutoSizeConfig.getInstance().isCustomFragment = true
      initChildApp()
      initARouter()
      if (com.logic.chat.BuildConfig.DEBUG) {
        ARouter.openDebug()
        ARouter.openLog()
      }
      ARouter.init(this)
    }
  }

  private fun initChildApp() {
    WebApp.onCreate(this)
//    ChatApp.onCreate(this)
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
