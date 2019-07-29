package com.logic.jellyfish.ui.timer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.data.entity.Event
import com.logic.jellyfish.ui.map.MapActivity
import com.logic.jellyfish.utils.ext.startActivity
import java.util.*

class TimerViewModel : ViewModel() {

   private val _speedNumber = MutableLiveData<String>()
   val speedNumber: LiveData<String> = _speedNumber

   private val _timeCostNumber = MutableLiveData<String>()
   val timeCostNumber: LiveData<String> = _timeCostNumber

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

   private var timer: Timer? = null

   /**
    * 继续按钮
    */
   fun resume() {
      startCount()
      _isPaused.value = false
      _resumeGather.value = Event(Unit)
   }

   /**
    * 暂停按钮
    */
   fun pause() {
      stopCount()
      _isPaused.value = true
      _pauseGather.value = Event(Unit)
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

   private var second = 0
   private var minute = 0

   fun startCount() {
      if (timer == null) timer = Timer()
      timer?.scheduleAtFixedRate(object : TimerTask() {
         override fun run() {
            if (second >= 59) {
               second = 0
               minute++
            } else {
               second++
            }
            _timeCostNumber.postValue("${minute}分:${second}秒")
         }
      }, 0L, 1000L)
   }

   private fun stopCount() {
      timer?.cancel()
      timer = null
   }

}