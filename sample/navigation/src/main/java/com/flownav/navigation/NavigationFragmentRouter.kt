package com.flownav.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.flownav.navigation.NavigationFragmentRoutes.FifthFragment.ACTION_NAME_FIFTH
import com.flownav.navigation.NavigationFragmentRoutes.FourthFragment.ACTION_NAME
import com.flownav.router.FlowNavFragmentRouter
import com.flownav.router.navigateTo
import com.flownav.router.workWithNavGraphOf

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

    fun navigateToFifth(lifecycleOwner: LifecycleOwner) {
        navigateTo(ACTION_NAME_FIFTH, lifecycleOwner)
    }
}
