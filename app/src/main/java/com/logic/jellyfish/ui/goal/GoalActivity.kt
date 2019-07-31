package com.logic.jellyfish.ui.goal

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.GoalActivityBinding

class GoalActivity : BaseActivity<GoalViewModel, GoalActivityBinding>(R.layout.goal_activity) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
