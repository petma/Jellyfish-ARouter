package com.logic.login.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter

class LoginViewModel : ViewModel() {

  fun login(v: View) {
    ARouter.getInstance().build("/app/main").navigation()
  }

  fun newUser(v: View) {
    ARouter.getInstance().build("/login/register").navigation()
  }

  fun forgetPassword(v: View) {
    ARouter.getInstance().build("/login/forget").navigation()
  }

}