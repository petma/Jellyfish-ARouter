package com.logic.jellyfish.ui.login.forget

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityForgetBinding

@Route(path = "/app/login/forget")
class ForgetActivity : BaseActivity<ForgetViewModel, ActivityForgetBinding>(
    R.layout.activity_forget
) {

    override fun init() {
        binding.viewmodel = viewModel
    }

}
