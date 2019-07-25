package com.logic.jellyfish.ui.ready

import android.view.View
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.timer.TimerActivity
import com.logic.jellyfish.utils.ext.navigateTo

class ReadyViewModel : ViewModel() {

   fun startRunning(v: View) {
      v.navigateTo<TimerActivity>()
   }
}