package com.logic.jellyfish

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

private const val BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION"

open class MainActivity : AppCompatActivity() {

    private var aMap: AMap? = null
    private var needCheckBackLocation = false
    private var isNeedCheck = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map_view.onCreate(savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        try {
            super.onResume()
            if (Build.VERSION.SDK_INT >= 23) {
                if (isNeedCheck) {
                    checkPermissions(needPermissions)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun init() {
        if (aMap == null) {
            aMap = map_view.map
        }
        aMap?.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(39.983456, 116.3154950), 0f, 0f, 0f)
            )
        )
    }

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private fun checkPermissions(permissions: Array<String>) {
        try {
            if (Build.VERSION.SDK_INT >= 23 && applicationInfo.targetSdkVersion >= 23) {
                val needRequestPermissionList = findDeniedPermissions(permissions)
                if (needRequestPermissionList != null && needRequestPermissionList.isNotEmpty()) {
                    try {
                        val array = needRequestPermissionList.toTypedArray()
                        val method = javaClass.getMethod(
                            "requestPermissions",
                            Array<String>::class.java, Int::class.javaPrimitiveType
                        )
                        method.invoke(this, array, 0)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }

                }
            }

        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    @TargetApi(23)
    private fun findDeniedPermissions(permissions: Array<String>): List<String>? {
        try {
            val needRequestPermissionList = ArrayList<String>()
            if (Build.VERSION.SDK_INT >= 23 && applicationInfo.targetSdkVersion >= 23) {
                for (perm in permissions) {
                    if (checkMySelfPermission(perm) != PackageManager.PERMISSION_GRANTED ||
                        shouldShowMyRequestPermissionRationale(perm)
                    ) {
                        if (!needCheckBackLocation && BACK_LOCATION_PERMISSION == perm) {
                            continue
                        }
                        needRequestPermissionList.add(perm)
                    }
                }
            }
            return needRequestPermissionList
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return null
    }

    private fun shouldShowMyRequestPermissionRationale(perm: String): Boolean {
        try {
            val method = javaClass.getMethod("shouldShowRequestPermissionRationale", String::class.java)
            return method.invoke(this, perm) as Boolean
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return false
    }

    private fun checkMySelfPermission(perm: String): Int {
        try {
            val method = javaClass.getMethod("checkSelfPermission", String::class.java)
            return method.invoke(this, perm) as Int
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return -1
    }

    /**
     * 需要进行检测的权限数组
     */
    private val needPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        BACK_LOCATION_PERMISSION
    )
}
