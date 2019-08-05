package com.logic.jellyfish.ui.mine.settings

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.mine.settings.about_us.AboutUsActivity
import com.logic.jellyfish.ui.mine.settings.change_phone.ChangePhoneActivity
import com.logic.jellyfish.ui.mine.settings.feedback.FeedbackActivity
import com.logic.jellyfish.utils.ext.startActivity

class SettingsViewModel : ViewModel() {

  val pushNotification = MutableLiveData<Boolean>()

  fun navToAboutUs(v: View) {
    v.startActivity<AboutUsActivity>()
  }

  fun navToFeedback(v: View) {
    v.startActivity<FeedbackActivity>()
  }

  fun navToChangePhoneNumber(v: View) {
    v.startActivity<ChangePhoneActivity>()
  }
}