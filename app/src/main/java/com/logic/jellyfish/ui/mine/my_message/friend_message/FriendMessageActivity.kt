package com.logic.jellyfish.ui.mine.my_message.friend_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityFriendMessageBinding

@Route(path = "/app/mine/my_message/friend_message")
class FriendMessageActivity : BaseActivity<FriendMessageViewModel, ActivityFriendMessageBinding>(
  R.layout.activity_friend_message
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.friend_msg)
  }
}