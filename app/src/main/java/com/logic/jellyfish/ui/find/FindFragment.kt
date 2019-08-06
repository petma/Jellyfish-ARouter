package com.logic.jellyfish.ui.find

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.FragmentFindBinding

@Route(path = "/app/find")
class FindFragment : BaseFragment<FindViewModel, FragmentFindBinding>(R.layout.fragment_find) {

  override fun init() {
    binding.viewmodel = viewModel
  }


}
