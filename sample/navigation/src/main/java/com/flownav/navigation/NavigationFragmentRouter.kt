package com.flownav.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.flownav.routerfragment.FlowNavFragmentNavigation.workWithNavGraphOf
import com.flownav.routerfragment.FlowNavFragmentRouter

object NavigationFragmentRouter : FlowNavFragmentRouter() {

    fun initNavGraphOn(activity: FragmentActivity, @IdRes navHost: Int) {
        workWithNavGraphOf(navHost, activity) {
            addDestination(
                isStartDestination = true,
                destinationKey = NavigationFragmentRoutes.FourthFragment.ACTION_NAME
            )
        }
    }
}