package com.logic.jellyfish.ui.mine.my_event

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityEventBinding

@Route(path = "/app/mine/my_event")
class EventActivity : BaseActivity<EventViewModel, ActivityEventBinding>(
  R.layout.activity_event
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.my_event)
  }

}