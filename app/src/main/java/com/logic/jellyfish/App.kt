package com.logic.jellyfish

import android.app.Application
import com.tencent.smtt.sdk.QbSdk

class App : Application() {

   override fun onCreate() {
      super.onCreate()
      app = this
      Cache.context = this
      initX5()
   }

   private fun initX5() {
      QbSdk.setDownloadWithoutWifi(true)
      QbSdk.initX5Environment(applicationContext, null)
   }

   companion object {
      lateinit var app: App private set
   }

}
