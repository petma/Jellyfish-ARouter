package com.logic.jellyfish.ui.ready

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.data.Event

class ReadyViewModel : ViewModel() {

   private val _startEvent = MutableLiveData<Event<Unit>>()
   val startEvent: LiveData<Event<Unit>> = _startEvent

   fun startRunning() {
      _startEvent.value = Event(Unit)
   }

}