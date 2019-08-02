package com.logic.jellyfish.ui.main


import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.MainFragmentBinding

class MainFragment : BaseFragment<MainViewModel, MainFragmentBinding>(R.layout.main_fragment) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
