package com.logic.jellyfish.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.logic.jellyfish.R
import kotlinx.android.synthetic.main.map_fragment.*

class MapFragment : Fragment() {

    private var aMap: AMap? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        initMap()
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

//    override fun onDestroy() {
//        super.onDestroy()
//        map_view.onDestroy()
//    }

    private fun initMap() {
        if (aMap == null) {
            aMap = map_view.map
        }
        aMap?.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(39.983456, 116.3154950), 0f, 0f, 0f)))
    }


}
