package com.sweet.login.forget

import com.alibaba.android.arouter.facade.annotation.Route
import com.sweet.login.R
import com.sweet.login.databinding.LoginActivityForgetBinding
import com.sweet.utils.BaseActivity

@Route(path = "/login/forget")
class ForgetActivity : BaseActivity<ForgetViewModel, LoginActivityForgetBinding>(
  R.layout.login_activity_forget
) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
