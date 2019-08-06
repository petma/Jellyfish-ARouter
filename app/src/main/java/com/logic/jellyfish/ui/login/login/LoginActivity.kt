package com.logic.jellyfish.ui.login.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityLoginBinding

@Route(path = "/app/login/login")
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(
    R.layout.activity_login
) {

    override fun init() {
        binding.viewmodel = viewModel
    }

}
