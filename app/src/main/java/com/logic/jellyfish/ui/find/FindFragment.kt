package com.logic.jellyfish.ui.find

import com.logic.jellyfish.R
import com.logic.jellyfish.databinding.FragmentFindBinding
import com.logic.utils.BaseFragment

class FindFragment : BaseFragment<FindViewModel, FragmentFindBinding>(R.layout.fragment_find) {

  override fun init() {
    binding.viewmodel = viewModel
  }


}
