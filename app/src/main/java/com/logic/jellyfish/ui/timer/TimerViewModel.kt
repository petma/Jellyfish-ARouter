package com.logic.jellyfish.ui.timer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.logic.jellyfish.R

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
        v.findNavController().navigate(R.id.action_timerFragment_to_mapActivity)

    }

    fun stop(v: View) {
        v.findNavController().navigate(R.id.action_timerFragment_to_x5WebViewActivity)
    }

    fun openMap(v: View) {
        v.findNavController().navigate(R.id.action_timerFragment_to_mapActivity)
    }

    fun lock() {

    }
}