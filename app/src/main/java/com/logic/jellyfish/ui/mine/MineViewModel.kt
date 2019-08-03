package com.logic.jellyfish.ui.mine

import android.view.View
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.mine.event.EventActivity
import com.logic.jellyfish.ui.mine.following.FollowingActivity
import com.logic.jellyfish.ui.mine.my_message.MyMessageActivity
import com.logic.jellyfish.ui.mine.record.RecordActivity
import com.logic.jellyfish.ui.mine.settings.SettingsActivity
import com.logic.jellyfish.utils.ext.startActivity

class MineViewModel : ViewModel() {

  fun navigateToRecord(v: View) {
    v.startActivity<RecordActivity>()
  }

  fun navigateToEvent(v: View) {
    v.startActivity<EventActivity>()
  }

  fun navigateToFollowing(v: View) {
    v.startActivity<FollowingActivity>()
  }

  fun navigateToMyMessage(v: View) {
    v.startActivity<MyMessageActivity>()
  }

  fun navigateToSettings(v: View) {
    v.startActivity<SettingsActivity>()
  }

}
