package com.sweet.login.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sweet.login.BuildConfig
import com.sweet.login.R
import com.sweet.login.databinding.LoginActivityLoginBinding
import com.sweet.utils.BaseActivity

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
