package com.logic.jellyfish.ui.mine

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.FragmentMineBinding

@Route(path = "/app/mine")
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>(R.layout.fragment_mine) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
