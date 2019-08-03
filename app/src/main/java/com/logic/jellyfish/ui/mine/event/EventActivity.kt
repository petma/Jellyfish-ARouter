package com.logic.jellyfish.ui.mine.event

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityEventBinding

class EventActivity : BaseActivity<EventViewModel, ActivityEventBinding>(
  R.layout.activity_event
) {

  override fun init() {
    binding.viewmodel = viewModel
  }

}