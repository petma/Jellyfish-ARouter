package com.logic.jellyfish.ui.mine.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter

class SettingsViewModel : ViewModel() {

  val pushNotification = MutableLiveData<Boolean>()

  fun navToAboutUs() {
    ARouter.getInstance().build("/app/mine/settings/about_us").navigation()
  }

  fun navToFeedback() {
    ARouter.getInstance().build("/app/mine/settings/feedback").navigation()
  }

  fun navToChangePhoneNumber() {
    ARouter.getInstance().build("/app/mine/settings/change_phone").navigation()
  }
}