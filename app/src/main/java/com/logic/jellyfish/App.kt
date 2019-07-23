package com.logic.jellyfish

import android.app.Application


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        lateinit var app: App
            private set

        var name: String? = null
        var sid: String? = null
        var tid: String? = null
    }

}
