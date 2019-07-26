package com.logic.jellyfish.ui.ready

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logic.jellyfish.data.Event

class ReadyViewModel : ViewModel() {

   val startEvent = MutableLiveData<Event<Unit>>()

   fun startRunning(v: View) {
      startEvent.value = Event(Unit)
   }
}