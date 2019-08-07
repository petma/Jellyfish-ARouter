package com.logic.mine.my_event

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityEventBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_event")
class EventActivity : BaseActivity<EventViewModel, ActivityEventBinding>(
  R.layout.activity_event
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.my_following)
  }

}