package com.logic.jellyfish.ui.mine

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.FragmentMineBinding

class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>(R.layout.fragment_mine) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
