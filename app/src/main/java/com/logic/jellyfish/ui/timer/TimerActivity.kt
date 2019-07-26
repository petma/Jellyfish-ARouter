package com.logic.jellyfish.ui.timer

import android.content.Intent
import android.view.KeyEvent
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.data.EventObserver
import com.logic.jellyfish.data.MessageEvent
import com.logic.jellyfish.databinding.TimerActivityBinding
import com.logic.jellyfish.service.TrackService
import org.greenrobot.eventbus.EventBus

class TimerActivity : BaseActivity<TimerViewModel, TimerActivityBinding>(R.layout.timer_activity) {

   private lateinit var trackIntent: Intent

   override fun init() {
      binding.viewmodel = viewModel
      viewModel.startCount()
      startTrackService()

      viewModel.resumeGather.observe(this, EventObserver {
         EventBus.getDefault().post(MessageEvent(MessageEvent.TYPE_RESUME_TRACK_SERVICE))
      })

      viewModel.pauseGather.observe(this, EventObserver {
         EventBus.getDefault().post(MessageEvent(MessageEvent.TYPE_PAUSE_TRACK_SERVICE))
      })
   }

   override fun onDestroy() {
      super.onDestroy()
      stopTrackService()
   }

   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
         return true
      }
      return super.onKeyDown(keyCode, event)
   }

   private fun startTrackService() {
      trackIntent = Intent(this, TrackService::class.java)
      startService(trackIntent)
   }

   private fun stopTrackService() {
      stopService(trackIntent)
   }
}