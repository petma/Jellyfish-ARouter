package com.logic.jellyfish.ui.ready

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logic.jellyfish.app.Cache
import com.logic.jellyfish.data.Event
import com.logic.jellyfish.http.RetrofitFactory
import com.logic.jellyfish.utils.Constants
import com.logic.jellyfish.utils.ext.getAndroidId
import com.logic.jellyfish.utils.ext.getString
import com.logic.jellyfish.utils.ext.saveString
import com.logic.jellyfish.utils.ext.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReadyViewModel : ViewModel() {

   private val _startEvent = MutableLiveData<Event<Unit>>()
   val startEvent: LiveData<Event<Unit>> = _startEvent

   private val _showProgress = MutableLiveData<Boolean>()
   val showProgress: LiveData<Boolean> = _showProgress

   fun startRunning(v: View) {
      // 先检查是否有提交过终端申请
      val terminalName = v.getString(Constants.PREF_KEY_TERMINAL_NAME)

      // 本地没有保存到号码,说明之前没有申请或者没有申请成功
      if (terminalName == null) {
         viewModelScope.launch {
            try {
               _showProgress.value = true
               val name = getAndroidId()
               val response = withContext(Dispatchers.IO) {
                  RetrofitFactory.aMapService.addTerminal(
                     Constants.TRACK_SERVICE_KEY,
                     Constants.SERVICE_ID,
                     name
                  )
               }
               when (response.errcode) {
                  // 创建新终端成功
                  10000 -> {
                     response.data?.let {
                        Cache.terminalName = name
                        v.saveString(Constants.PREF_KEY_TERMINAL_NAME, name)
                        _startEvent.value = Event(Unit)
                     }
                  }
                  // 终端已存在,理论上应该不会走到这一条
                  20009 -> {
                     Cache.terminalName = name
                     v.saveString(Constants.PREF_KEY_TERMINAL_NAME, name)
                     _startEvent.value = Event(Unit)
                     v.toast("终端已存在")
                  }
                  // 创建失败
                  else -> {
                     v.toast("创建终端失败")
                  }
               }
            } catch (e: Exception) {
               v.toast("网络异常")
            } finally {
               _showProgress.value = false
            }
         }
      } else {
         Cache.terminalName = terminalName
         _startEvent.value = Event(Unit)
      }

   }

}