package com.logic.jellyfish.ui.mine.my_message

import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter

class MyMessageViewModel : ViewModel() {

  fun navToNotificationMessage() {
    ARouter.getInstance().build("/app/mine/my_message/notification_message").navigation()
  }

  fun navToFriendMessage() {
    ARouter.getInstance().build("/app/mine/my_message/friend_message").navigation()
  }

  fun navToGroupMessage() {
    ARouter.getInstance().build("/app/mine/my_message/group_message").navigation()
  }
}