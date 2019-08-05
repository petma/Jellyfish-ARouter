package com.logic.jellyfish.ui.login.forget

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.login.login.LoginActivity
import com.logic.jellyfish.utils.ext.startActivity

class ForgetViewModel : ViewModel() {

  val phoneNumber = MutableLiveData<String>()
  val password = MutableLiveData<String>()
  val verificationCode = MutableLiveData<String>()

  fun confirm(v: View) {
    v.startActivity<LoginActivity>()
  }
}
