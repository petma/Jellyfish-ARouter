package com.logic.mine.my_following

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityFollowingBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_following")
class FollowingActivity : BaseActivity<FollowingViewModel, ActivityFollowingBinding>(
  R.layout.activity_following
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.my_following)
  }

}