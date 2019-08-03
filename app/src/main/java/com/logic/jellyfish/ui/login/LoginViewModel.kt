package com.logic.jellyfish.ui.login

import android.view.View
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.MainActivity
import com.logic.jellyfish.utils.ext.startActivity

class LoginViewModel : ViewModel() {

  fun login(v: View) {
    v.startActivity<MainActivity>()
  }
}