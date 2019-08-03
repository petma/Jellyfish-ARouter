package com.logic.jellyfish.ui.login

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.LoginActivityBinding

class LoginActivity : BaseActivity<LoginViewModel, LoginActivityBinding>(R.layout.login_activity) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
