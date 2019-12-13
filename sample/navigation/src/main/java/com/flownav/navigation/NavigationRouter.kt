package com.flownav.navigation

import android.content.Context
import com.flownav.navigation.NavigationRoutes.FEATURE_1
import com.flownav.navigation.NavigationRoutes.FEATURE_2
import com.flownav.navigation.NavigationRoutes.FEATURE_3
import com.flownav.router.FlowNavRouter

object NavigationRouter : FlowNavRouter() {

    fun openFeature1(context: Context) = context.start(FEATURE_1)

    fun openFeature2(
        context: Context,
        param: String
    ) = context.start(FEATURE_2) {
        putString(SecondActionArgKeys.arg1, param)
    }

    fun openFeature3(context: Context) = context.start(FEATURE_3)
}
