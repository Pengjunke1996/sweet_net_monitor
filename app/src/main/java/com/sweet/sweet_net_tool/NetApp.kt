package com.sweet.sweet_net_tool

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration

class NetApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        registerComponentCallbacks(object :ComponentCallbacks{
            override fun onConfigurationChanged(p0: Configuration) {

            }

            override fun onLowMemory() {

            }

        })
    }
}