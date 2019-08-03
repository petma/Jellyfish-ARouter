package com.logic.jellyfish.ui.mine.my_message

import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityMyMessageBinding

class MyMessageActivity : BaseActivity<MyMessageViewModel, ActivityMyMessageBinding>(
  R.layout.activity_my_message
) {

  override fun init() {
    binding.viewmodel = viewModel
  }
}