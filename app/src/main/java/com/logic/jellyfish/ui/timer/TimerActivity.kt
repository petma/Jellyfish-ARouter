package com.logic.jellyfish.ui.timer

import android.content.Intent
import android.view.KeyEvent
import com.logic.jellyfish.R
import com.logic.jellyfish.base.BaseActivity
import com.logic.jellyfish.data.entity.TimerEvent
import com.logic.jellyfish.databinding.TimerActivityBinding
import com.logic.jellyfish.service.LocationService
import com.logic.jellyfish.service.TrackService
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TimerActivity : BaseActivity<TimerViewModel, TimerActivityBinding>(R.layout.timer_activity) {

  private var locationIntent: Intent? = null
  private var trackIntent: Intent? = null

  override fun init() {
    binding.viewmodel = viewModel
    EventBus.getDefault().register(this)
//      startLocationService()
    startTrackService()
  }

  override fun onDestroy() {
    super.onDestroy()
    EventBus.getDefault().unregister(this)
//      stopLocationService()
    stopTrackService()
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      return true
    }
    return super.onKeyDown(keyCode, event)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onTimerEvent(event: TimerEvent) {
    viewModel.timeCostNumber.value = "${event.minute}分${event.second}秒"
    viewModel.speedNumber.value = "${event.speed}米/秒"
  }

  private fun startLocationService() {
    locationIntent = Intent(this, LocationService::class.java)
    startService(locationIntent)
  }

  private fun startTrackService() {
    trackIntent = Intent(this, TrackService::class.java)
    startService(trackIntent)
  }

  private fun stopLocationService() {
    locationIntent?.let {
      stopService(it)
    }
  }

  private fun stopTrackService() {
    trackIntent?.let {
      stopService(it)
    }
  }
}