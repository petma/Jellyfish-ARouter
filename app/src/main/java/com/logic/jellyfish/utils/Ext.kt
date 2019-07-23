package com.logic.jellyfish.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng

fun AMap.moveCamera(latitude: Double, longitude: Double) {
    moveCamera(
        CameraUpdateFactory.newCameraPosition(
            CameraPosition(LatLng(latitude, longitude), 18f, 0f, 0f)
        )
    )
}

fun View.toast(content: String) {
    Toast.makeText(this.context, content, Toast.LENGTH_LONG).show()
    log(content)
}

fun Activity.toast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    log(content)
}

fun log(content: String) {
    Log.v("测试", "\n$content\n")
}

