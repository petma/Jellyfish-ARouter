package com.logic.jellyfish.ui.mine.my_following

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityFollowingBinding

@Route(path = "/app/mine/my_following")
class FollowingActivity : BaseActivity<FollowingViewModel, ActivityFollowingBinding>(
  R.layout.activity_following
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.my_following)
  }

}