package com.logic.login.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.login.BuildConfig
import com.logic.login.R
import com.logic.login.databinding.LoginActivityLoginBinding
import com.logic.utils.BaseActivity

@Route(path = "/login/login")
class LoginActivity : BaseActivity<LoginViewModel, LoginActivityLoginBinding>(
  R.layout.login_activity_login
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
