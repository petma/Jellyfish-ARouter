package com.logic.mine.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.mine.BuildConfig
import com.logic.mine.R
import com.logic.mine.databinding.ActivityMineBinding
import com.logic.utils.BaseActivity

@Route(path = "/login/login")
class MineActivity : BaseActivity<MineViewModel, ActivityMineBinding>(
  R.layout.activity_mine
) {

  override fun init() {
    if (BuildConfig.DEBUG) {
      ARouter.openLog()
      ARouter.openDebug()
    }
    ARouter.init(application)
    binding.viewmodel = viewModel
  }

}
