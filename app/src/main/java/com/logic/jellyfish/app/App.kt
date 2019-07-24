package com.logic.jellyfish.app

import android.app.Application
import com.tencent.smtt.sdk.QbSdk


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        initX5()
    }

    private fun initX5() {
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(applicationContext, null)
    }

    companion object {
        lateinit var app: App
            private set

        var name: String? = null
        var sid: String? = null
        var tid: String? = null
    }

}
