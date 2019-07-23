package com.logic.jellyfish.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.logic.jellyfish.App
import com.logic.jellyfish.R
import com.logic.jellyfish.http.RetrofitFactory
import com.logic.jellyfish.utils.TRACK_SERVICE_KEY
import com.logic.jellyfish.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _aMapServiceDataString = MutableLiveData<String>()
    val aMapServiceDataString: LiveData<String> = _aMapServiceDataString

    val serviceValue = MutableLiveData<String>()

    val serviceDesc = MutableLiveData<String>()

    fun startRunning(v: View) {
        v.findNavController().navigate(R.id.action_mainFragment_to_mapActivity)
    }

    fun getService(v: View) {
        val hospitalService = RetrofitFactory.hospitalService
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val memberList = hospitalService.getTerMembersList()
                withContext(Dispatchers.Main) {
                    if (memberList.success) {
                        v.toast("成功")
                    }
                }
            } catch (e: Throwable) {

            }
        }
    }

    fun createService(v: View) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitFactory.aMapService.addService(
                    TRACK_SERVICE_KEY,
                    serviceValue.value
                )
                withContext(Dispatchers.Main) {
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
                            "${_aMapServiceDataString.value}\n${Gson().toJson(response)}"
                    }
                }
            } catch (e: Throwable) {
                v.toast("异常")
            }
        }
    }

    fun createTerminal(v: View) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitFactory.aMapService.addTerminal(
                    TRACK_SERVICE_KEY,
                    App.sid,
                    App.name
                )
                withContext(Dispatchers.Main) {
                    App.tid = response.data?.tid
                    if (_aMapServiceDataString.value == null) {
                        _aMapServiceDataString.value = Gson().toJson(response)
                    } else {
                        _aMapServiceDataString.value =
                            "${_aMapServiceDataString.value}\n${Gson().toJson(response)}"
                    }
                }
            } catch (e: Throwable) {
                v.toast("异常")
            }
        }
    }

    fun clearData() {
        _aMapServiceDataString.value = null
    }

}
