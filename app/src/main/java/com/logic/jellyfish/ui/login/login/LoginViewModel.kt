package com.logic.jellyfish.ui.login.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.MainActivity
import com.logic.jellyfish.ui.login.forget.ForgetActivity
import com.logic.jellyfish.ui.login.register.RegisterActivity
import com.logic.jellyfish.utils.ext.startActivity

class LoginViewModel : ViewModel() {

  fun login(v: View) {
    v.startActivity<MainActivity>()
  }

  fun newUser(v: View) {
    v.startActivity<RegisterActivity>()
  }

  fun forgetPassword(v: View) {
    v.startActivity<ForgetActivity>()
  }

}