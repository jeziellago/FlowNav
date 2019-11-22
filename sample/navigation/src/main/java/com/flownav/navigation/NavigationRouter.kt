package com.flownav.navigation


import android.content.Context
import com.flownav.navigation.NavigationRoutes.FEATURE_1
import com.flownav.navigation.NavigationRoutes.FEATURE_2
import com.flownav.router.FlowNavRouter

object NavigationRouter : FlowNavRouter() {

    fun openFeature1(context: Context) = context.open(FEATURE_1)

    fun openFeature2(
        context: Context,
        arg1: String
    ) = context.open(FEATURE_2) withParams {
        putString(SecondActionArgKeys.arg1, arg1)
    }
}