package com.flownav.sample

import android.app.Application
import com.flownav.annotation.FlowNavMain
import com.flownav.router.FlowNavApp
import com.flownav.router.navMap
import com.flownav.router.navMapFragment

@FlowNavMain
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FlowNavApp.start(navMap(), navMapFragment())
    }
}
