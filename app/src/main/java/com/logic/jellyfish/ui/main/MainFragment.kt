package com.logic.jellyfish.ui.main

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.FragmentMainBinding

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(R.layout.fragment_main) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
