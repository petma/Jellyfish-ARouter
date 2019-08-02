package com.logic.jellyfish.ui.login

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.LoginFragmentBinding

class LoginFragment : BaseFragment<LoginViewModel, LoginFragmentBinding>(R.layout.login_fragment) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
