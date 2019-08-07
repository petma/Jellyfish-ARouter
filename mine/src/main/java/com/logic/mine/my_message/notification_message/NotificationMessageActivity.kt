package com.logic.mine.my_message.notification_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.mine.R
import com.logic.mine.databinding.ActivityNotificationMessageBinding
import com.logic.utils.BaseActivity

@Route(path = "/mine/my_message/notification_message")
class NotificationMessageActivity : BaseActivity<NotificationMessageViewModel, ActivityNotificationMessageBinding>(
  R.layout.activity_notification_message
) {

  override fun init() {
    binding.viewmodel = viewModel
//    toolbar.title = getString(R.string.notification_msg)
  }
}