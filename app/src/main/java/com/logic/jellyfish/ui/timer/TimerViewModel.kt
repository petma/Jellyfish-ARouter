package com.logic.jellyfish.ui.timer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.data.entity.Event
import com.logic.jellyfish.data.entity.MessageEvent
import com.logic.jellyfish.ui.map.MapActivity
import com.logic.jellyfish.utils.ext.startActivity
import org.greenrobot.eventbus.EventBus

class TimerViewModel : ViewModel() {

   val speedNumber = MutableLiveData<String>()

   val timeCostNumber = MutableLiveData<String>()

   private val _caloriesConsumeNumberNumber = MutableLiveData<String>()
   val caloriesConsumeNumberNumber: LiveData<String> = _caloriesConsumeNumberNumber

   private val _kiloMeterNumber = MutableLiveData<String>()
   val kiloMeterNumber: LiveData<String> = _kiloMeterNumber

   private val _weather = MutableLiveData<String>()
   val weather: LiveData<String> = _weather

   private val _isPaused = MutableLiveData<Boolean>()
   val isPaused: LiveData<Boolean> = _isPaused

   private val _resumeGather = MutableLiveData<Event<Unit>>()
   val resumeGather: LiveData<Event<Unit>> = _resumeGather

   private val _pauseGather = MutableLiveData<Event<Unit>>()
   val pauseGather: LiveData<Event<Unit>> = _pauseGather

   /**
    * 继续按钮
    */
   fun resume() {
      _isPaused.value = false
      _resumeGather.value = Event(Unit)
      EventBus.getDefault().post(MessageEvent(MessageEvent.TYPE_RESUME_TRACK_SERVICE))
   }

   /**
    * 暂停按钮
    */
   fun pause() {
      _isPaused.value = true
      _pauseGather.value = Event(Unit)
      EventBus.getDefault().post(MessageEvent(MessageEvent.TYPE_PAUSE_TRACK_SERVICE))
   }

   /**
    * 停止按钮
    */
   fun stop(v: View) {
      (v.context as TimerActivity).finish()
   }

   fun openMap(v: View) {
      v.startActivity<MapActivity>()
   }

   fun lock() {
   }

}