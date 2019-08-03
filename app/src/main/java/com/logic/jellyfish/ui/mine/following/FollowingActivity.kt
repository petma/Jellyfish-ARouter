package com.logic.jellyfish.ui.mine.following

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityFollowingBinding

class FollowingActivity : BaseActivity<FollowingViewModel, ActivityFollowingBinding>(
  R.layout.activity_following
) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}