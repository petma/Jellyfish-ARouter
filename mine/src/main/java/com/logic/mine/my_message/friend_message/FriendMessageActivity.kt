package com.logic.mine.my_message.friend_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityFriendMessageBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_message/friend_message")
class FriendMessageActivity : BaseActivity<FriendMessageViewModel, ActivityFriendMessageBinding>(
  R.layout.activity_friend_message
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.friend_msg)
  }
}