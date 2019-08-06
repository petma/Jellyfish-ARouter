package com.logic.jellyfish.ui.mine.my_message.group_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityGroupMessageBinding

@Route(path = "/app/mine/my_message/group_message")
class GroupMessageActivity : BaseActivity<GroupMessageViewModel, ActivityGroupMessageBinding>(
  R.layout.activity_group_message
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.group_msg)
  }
}