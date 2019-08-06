package com.logic.jellyfish.ui.sport

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseFragment
import com.logic.jellyfish.databinding.FragmentSportBinding

@Route(path = "/app/sport")
class SportFragment : BaseFragment<SportViewModel, FragmentSportBinding>(R.layout.fragment_sport) {
  override fun init() {
  }


}
