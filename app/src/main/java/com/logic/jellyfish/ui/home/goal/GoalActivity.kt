package com.logic.jellyfish.ui.home.goal

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityGoalBinding

@Route(path = "/app/home/goal")
class GoalActivity : BaseActivity<GoalViewModel, ActivityGoalBinding>(R.layout.activity_goal) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}
