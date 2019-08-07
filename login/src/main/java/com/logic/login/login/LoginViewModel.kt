package com.logic.login.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.utils.ext.toast
import com.logic.utils.http.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

  val phoneNumber = MutableLiveData<String>()
  val password = MutableLiveData<String>()

  fun login(v: View) {
    viewModelScope.launch {
      try {
        val response = withContext(Dispatchers.IO) {
          RetrofitFactory.jellyfishService.auth(
            phoneNumber.value,
            password.value
          )
        }
        if (response.success) {
          ARouter.getInstance().build("/app/main").navigation()
        }
      } catch (e: Exception) {
        v.toast("网络错误")
      }
    }

  }

  fun newUser(v: View) {
    ARouter.getInstance().build("/login/register").navigation()
  }

  fun forgetPassword(v: View) {
    ARouter.getInstance().build("/login/forget").navigation()
  }

}