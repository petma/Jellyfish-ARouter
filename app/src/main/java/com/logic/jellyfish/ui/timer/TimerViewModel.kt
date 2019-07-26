package com.logic.jellyfish.ui.timer

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.data.Event
import com.logic.jellyfish.ui.map.MapActivity
import com.logic.jellyfish.utils.ext.startActivity
import java.util.*

class TimerViewModel : ViewModel() {

   val speedNumber = MutableLiveData<String>()
   val timeCostNumber = MutableLiveData<String>()
   val caloriesConsumeNumberNumber = MutableLiveData<String>()
   val kiloMeterNumber = MutableLiveData<String>()
   val weather = MutableLiveData<String>()
   val isPaused = MutableLiveData<Boolean>()
   val resumeGather = MutableLiveData<Event<Unit>>()
   val pauseGather = MutableLiveData<Event<Unit>>()

   private var timer: Timer? = null

   /**
    * 继续按钮
    */
   fun resume() {
      isPaused.value = false
      startCount()
      resumeGather.value = Event(Unit)
   }

   /**
    * 停止按钮
    */
   fun stop(v: View) {
      (v.context as TimerActivity).finish()
   }

   /**
    * 暂停按钮
    */
   fun pause() {
      isPaused.value = true
      stopCount()
      pauseGather.value = Event(Unit)
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
            timeCostNumber.postValue("${minute}分:${second}秒")
         }
      }, 0L, 1000L)
   }

   private fun stopCount() {
      timer?.cancel()
      timer = null
   }

}