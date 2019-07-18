package com.logic.jellyfish.main

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.logic.jellyfish.R
import com.logic.jellyfish.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    private var needCheckBackLocation = false
    private var isNeedCheck = true

    private var viewModel: MainViewModel? = null
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        binding = DataBindingUtil.bind(view)!!
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private fun checkPermissions(permissions: Array<String>) {
        try {
            if (Build.VERSION.SDK_INT >= 23 && activity?.applicationInfo!!.targetSdkVersion >= 23) {
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
            if (Build.VERSION.SDK_INT >= 23 && activity!!.applicationInfo.targetSdkVersion >= 23) {
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

    companion object{
        private const val BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION"
    }

}
