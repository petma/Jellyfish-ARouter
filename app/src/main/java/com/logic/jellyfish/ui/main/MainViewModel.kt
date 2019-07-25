package com.logic.jellyfish.ui.main

import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.logic.jellyfish.R
import com.logic.jellyfish.app.App
import com.logic.jellyfish.http.RetrofitFactory
import com.logic.jellyfish.ui.timer.TimerActivity
import com.logic.jellyfish.utils.Constants.TRACK_SERVICE_KEY
import com.logic.jellyfish.utils.ext.navigateTo
import com.logic.jellyfish.utils.ext.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

   private val _aMapServiceDataString = MutableLiveData<String>()
   val aMapServiceDataString: LiveData<String> = _aMapServiceDataString

   val serviceName = MutableLiveData<String>()

   val serviceDesc = MutableLiveData<String>()

   fun startRunning(v: View) {
      v.navigateTo<TimerActivity>()
   }

   fun go(hello: LinearLayout) {
      hello.setBackgroundResource(R.color.colorAccent)
   }

   fun createService(v: View) {
      viewModelScope.launch {
         try {
            val response = withContext(Dispatchers.IO) {
               RetrofitFactory.aMapService.addService(
                  TRACK_SERVICE_KEY,
                  serviceName.value
               )
            }
            when (response.errcode) {
               10000 -> {
                  v.toast("成功${response.errdetail}")
                  App.name = response.data?.name
                  App.sid = response.data?.sid
               }
               20009 -> {
                  v.toast("已存在${response.errdetail}")
               }
               else -> {
                  v.toast("失败${response.errdetail}")
               }
            }
            if (_aMapServiceDataString.value == null) {
               _aMapServiceDataString.value = Gson().toJson(response)
            } else {
               _aMapServiceDataString.value =
                  "${_aMapServiceDataString.value}你好${Gson().toJson(response)}"
            }
         } catch (e: Exception) {
            v.toast("网络异常")
         }

      }
   }

   fun createTerminal(v: View) {
      viewModelScope.launch {
         try {
            val response = withContext(Dispatchers.IO) {
               RetrofitFactory.aMapService.addTerminal(
                  TRACK_SERVICE_KEY,
                  App.sid,
                  App.name
               )
            }
            App.tid = response.data?.tid
            if (_aMapServiceDataString.value == null) {
               _aMapServiceDataString.value = Gson().toJson(response)
            } else {
               _aMapServiceDataString.value =
                  "${_aMapServiceDataString.value}\n${Gson().toJson(response)}"
            }
         } catch (e: Exception) {
            v.toast("网络异常")
         }
      }
   }

   fun clearData() {
      _aMapServiceDataString.value = null
   }

}
