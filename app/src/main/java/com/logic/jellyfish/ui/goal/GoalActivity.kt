package com.logic.jellyfish.ui.goal

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityGoalBinding

class GoalActivity : BaseActivity<GoalViewModel, ActivityGoalBinding>(R.layout.activity_goal) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
