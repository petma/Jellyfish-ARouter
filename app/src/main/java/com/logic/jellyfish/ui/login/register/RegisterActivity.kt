package com.logic.jellyfish.ui.login.register

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(
  R.layout.activity_register
) {

  override fun init() {
    binding.viewmodel = viewModel
  }


}