package com.logic.jellyfish.ui.login.forget

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityForgetBinding

class ForgetActivity : BaseActivity<ForgetViewModel, ActivityForgetBinding>(
  R.layout.activity_forget
) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
