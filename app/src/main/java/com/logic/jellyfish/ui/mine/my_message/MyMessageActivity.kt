package com.logic.jellyfish.ui.mine.my_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityMyMessageBinding

@Route(path = "/app/mine/my_message")
class MyMessageActivity : BaseActivity<MyMessageViewModel, ActivityMyMessageBinding>(
  R.layout.activity_my_message
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.my_message)
  }
}