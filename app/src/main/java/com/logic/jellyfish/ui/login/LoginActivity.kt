package com.logic.jellyfish.ui.login

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
