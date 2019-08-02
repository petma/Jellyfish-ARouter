package com.logic.jellyfish.ui.login

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.logic.jellyfish.R

class LoginViewModel : ViewModel() {

  fun login(v: View) {
    v.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
  }
}