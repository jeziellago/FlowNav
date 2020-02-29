package com.flownav.sample.otherapp

import android.app.Application
import com.flownav.annotation.FlowNavMain
import com.flownav.router.FlowNavApp
import com.flownav.router.extension.getEntryMap

@FlowNavMain
class MyOtherAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FlowNavApp.start(getEntryMap())
    }
}
