package com.logic.jellyfish.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.logic.jellyfish.R
import kotlinx.android.synthetic.main.map_fragment.*

class MapActivity : AppCompatActivity() {

    private var aMap: AMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_activity)
        map_view.onCreate(savedInstanceState)
        initMap()
    }

    private fun initMap() {
        if (aMap == null) {
            aMap = map_view.map
        }
        aMap?.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(39.983456, 116.3154950), 0f, 0f, 0f)
            ))
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_view.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view.onDestroy()
    }
}
