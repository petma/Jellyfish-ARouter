package com.logic.jellyfish.ui.login.register

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.login.login.LoginActivity
import com.logic.jellyfish.utils.ext.startActivity
import com.logic.jellyfish.utils.ext.toast

class RegisterViewModel : ViewModel() {

  val phoneNumber = MutableLiveData<String>()
  val verificationCode = MutableLiveData<String>()
  val password = MutableLiveData<String>()
  val confirmPassword = MutableLiveData<String>()
  val agreement = MutableLiveData<Boolean>()

  fun register(v: View) {
    if (TextUtils.isEmpty(confirmPassword.value)) {
      v.toast("请输入正确的手机号")
      return
    }
    if (TextUtils.isEmpty(verificationCode.value)) {
      v.toast("请输入正确的验证码")
      return
    }
    if (TextUtils.isEmpty(password.value)) {
      v.toast("请输入正确的密码")
      return
    }
    if (TextUtils.isEmpty(confirmPassword.value)) {
      v.toast("两次密码不相同")
      return
    }
    if (agreement.value == false) {
      v.toast("请先阅读协议条款并同意")
      return
    }
    v.toast("注册成功")
    v.startActivity<LoginActivity>()
  }

  fun navigateToAgreement(v: View) {

  }

  fun navigateToLogin(v: View) {
    v.startActivity<LoginActivity>()
  }
}