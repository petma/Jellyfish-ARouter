package com.logic.mine.my_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityMyMessageBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_message")
class MyMessageActivity : BaseActivity<MyMessageViewModel, ActivityMyMessageBinding>(
  R.layout.activity_my_message
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.my_message)
  }
}