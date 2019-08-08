package com.logic.chat

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.utils.Cache
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.util.NIMUtil

class cApp : Application() {

  override fun onCreate() {
    super.onCreate()

    NIMClient.init(this, null, null)

    if (NIMUtil.isMainProcess(this)) {
      // 主缓存
      Cache.init(this)
      if (BuildConfig.DEBUG) {
        ARouter.openDebug()
        ARouter.openLog()
      }
      ARouter.init(this)
    }
  }

}