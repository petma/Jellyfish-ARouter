package com.logic.jellyfish.ui.timer

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.map.MapActivity
import com.logic.jellyfish.ui.web.X5WebViewActivity
import com.logic.jellyfish.utils.ext.navigateTo
import java.util.*

class TimerViewModel : ViewModel() {

   val speedNumber = MutableLiveData<String>()

   val timeCostNumber = MutableLiveData<String>()

   val caloriesConsumeNumberNumber = MutableLiveData<String>()

   val kiloMeterNumber = MutableLiveData<String>()

   val weather = MutableLiveData<String>()

   val isStopped = MutableLiveData<Boolean>()

   private var timer: Timer? = null

   fun resume() {
      isStopped.value = false
      startCount()
   }

   fun suspend() {
      isStopped.value = true
      stopCount()
   }


   fun stop(v: View) {
      v.navigateTo<X5WebViewActivity>()
   }

   fun openMap(v: View) {
      v.navigateTo<MapActivity>()
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