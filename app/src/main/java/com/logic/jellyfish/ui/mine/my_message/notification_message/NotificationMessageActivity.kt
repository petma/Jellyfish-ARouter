package com.logic.jellyfish.ui.mine.my_message.notification_message

import com.alibaba.android.arouter.facade.annotation.Route
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.databinding.ActivityNotificationMessageBinding

@Route(path = "/app/mine/my_message/notification_message")
class NotificationMessageActivity : BaseActivity<NotificationMessageViewModel, ActivityNotificationMessageBinding>(
  R.layout.activity_notification_message
) {

  override fun init() {
    binding.viewmodel = viewModel
    toolbar.title = getString(R.string.notification_msg)
  }
}