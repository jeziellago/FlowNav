package com.flownav.sample

import android.app.Application
import com.flownav.annotation.FlowNavMain
import com.flownav.navigation.NavigationRouter

@FlowNavMain
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NavigationRouter.init(FlowNavActions.get())
    }
}