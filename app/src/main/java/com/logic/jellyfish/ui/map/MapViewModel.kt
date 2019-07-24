package com.logic.jellyfish.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.data.Event

class MapViewModel : ViewModel() {

    private val _startService = MutableLiveData<Event<Unit>>()
    val startService: LiveData<Event<Unit>> = _startService

    private val _startGather = MutableLiveData<Event<Unit>>()
    val startGather: LiveData<Event<Unit>> = _startGather

    fun startService() {
        _startService.value = Event(Unit)
    }

    fun startGather() {
        _startGather.value = Event(Unit)
    }
}