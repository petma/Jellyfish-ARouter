package com.sweet.login.register

import com.alibaba.android.arouter.facade.annotation.Route
import com.sweet.login.R
import com.sweet.login.databinding.LoginActivityRegisterBinding
import com.sweet.utils.BaseActivity

@Route(path = "/login/register")
class RegisterActivity : BaseActivity<RegisterViewModel, LoginActivityRegisterBinding>(
  R.layout.login_activity_register
) {

    override fun init() {
        binding.viewmodel = viewModel
    }


}