package com.logic.jellyfish.ui.timer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.ui.map.MapActivity
import com.logic.jellyfish.ui.web.X5WebViewActivity
import com.logic.jellyfish.utils.navigateTo

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

    fun resume(v: View) {
//        v.findNavController().navigate(R.id.action_timerFragment_to_mapActivity)
//        v.navigateTo<MapActivity>()
    }

    fun stop(v: View) {
//        v.findNavController().navigate(R.id.action_timerFragment_to_x5WebViewActivity)
        v.navigateTo<X5WebViewActivity>()
    }

    fun openMap(v: View) {
//        v.findNavController().navigate(R.id.action_timerFragment_to_mapActivity)
        v.navigateTo<MapActivity>()
    }

    fun lock() {

    }
}