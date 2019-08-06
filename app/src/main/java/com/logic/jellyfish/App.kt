package com.logic.jellyfish

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.smtt.sdk.QbSdk

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    app = this
    Cache.context = this
    initARouter()
    initX5()
  }

  private fun initARouter() {
    if (BuildConfig.DEBUG) {
      ARouter.openDebug()
      ARouter.openLog()
    }
    ARouter.init(this)
  }

  private fun initX5() {
    QbSdk.setDownloadWithoutWifi(true)
    QbSdk.initX5Environment(applicationContext, null)
  }

  companion object {
    lateinit var app: App private set
  }

}
