package com.logic.jellyfish.ui.login.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter

class LoginViewModel : ViewModel() {

  fun login(v: View) {
    ARouter.getInstance().build("/app/main").navigation()
//    v.startActivity<MainActivity>()
  }

  fun newUser(v: View) {
//    v.startActivity<RegisterActivity>()
    ARouter.getInstance().build("/app/login/register").navigation()
  }

  fun forgetPassword(v: View) {
    ARouter.getInstance().build("/app/login/forget").navigation()
//    v.startActivity<ForgetActivity>()
  }

}