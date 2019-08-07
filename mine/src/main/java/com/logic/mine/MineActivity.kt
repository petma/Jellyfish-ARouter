package com.logic.mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

class MineActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    if (BuildConfig.DEBUG) {
      ARouter.openLog()
      ARouter.openDebug()
    }
    ARouter.init(application)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_mine)
  }
}
