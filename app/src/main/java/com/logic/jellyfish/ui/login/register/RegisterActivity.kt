package com.logic.jellyfish.ui.login.register

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityRegisterBinding

@Route(path = "/app/login/register")
class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(
        R.layout.activity_register, false
) {

    override fun init() {
        binding.viewmodel = viewModel
    }


}