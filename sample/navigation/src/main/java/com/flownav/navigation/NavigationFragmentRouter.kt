package com.flownav.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.flownav.navigation.NavigationFragmentRoutes.FifthFragment.ACTION_NAME_FIFTH
import com.flownav.navigation.NavigationFragmentRoutes.FourthFragment.ACTION_NAME
import com.flownav.routerfragment.FlowNavFragmentNavigation.navigateTo
import com.flownav.routerfragment.FlowNavFragmentNavigation.workWithNavGraphOf
import com.flownav.routerfragment.FlowNavFragmentRouter

object NavigationFragmentRouter : FlowNavFragmentRouter() {

    fun initNavGraphOn(activity: FragmentActivity, @IdRes navHost: Int) {
        workWithNavGraphOf(navHost, activity) {
            addDestination(
                isStartDestination = true,
                destinationKey = ACTION_NAME
            )?. withActions {
                put(ACTION_NAME, ACTION_NAME_FIFTH)
            }

            addDestination(
                destinationKey = ACTION_NAME_FIFTH
            )
        }
    }

    fun navigateToFifth() {
        navigateTo(ACTION_NAME_FIFTH)
    }
}