package com.logic.jellyfish.ui.sport.ready

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.logic.jellyfish.Cache
import com.logic.jellyfish.data.entity.Event
import com.logic.jellyfish.data.http.RetrofitFactory
import com.logic.jellyfish.data.room.RoomFactory
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

  fun startGPSLocation() {
//    Cache.isLocationSDK = true
//    _startEvent.value = Event(Unit)
    ARouter.getInstance().build("/login/login").navigation()
  }

  fun startLieYing(v: View) {
    Cache.isLocationSDK = false
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
            // 终端已存在,说明之前保存的SharedPreferences记录被清掉了
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

  fun deleteTrack(v: View) {
    viewModelScope.launch {
      RoomFactory.repository.deleteLatLngs()
      v.toast("删除成功")
    }
  }

}