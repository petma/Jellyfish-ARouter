package com.logic.chat.chatroom

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.chat.R
import com.logic.chat.databinding.ActivityChatroomBinding
import com.logic.utils.BaseActivity

@Route(path = "/chat/chatroom")
class ChatRoomActivity : BaseActivity<ChatRoomViewModel, ActivityChatroomBinding>(
  R.layout.activity_chatroom
) {

  override fun init() {

  }
}