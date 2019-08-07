package com.logic.mine.my_message.group_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityGroupMessageBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_message/group_message")
class GroupMessageActivity : BaseActivity<GroupMessageViewModel, ActivityGroupMessageBinding>(
  R.layout.activity_group_message
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.group_msg)
  }
}