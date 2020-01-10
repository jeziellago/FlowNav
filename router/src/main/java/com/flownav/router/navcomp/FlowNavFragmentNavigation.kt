/*
 * Copyright 2019, Jeziel Lago - Alex Soares.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.flownav.router.navcomp

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.flownav.router.FlowNavApp
import com.flownav.router.extension.getGraphOr

fun FlowNavFragmentRouter.workWithNavGraphOf(
    @IdRes navHost: Int,
    activity: FragmentActivity,
    navigateFragment: FlowNavFragmentRouter.() -> Unit
) {
    val navHostFragment =
        activity.supportFragmentManager.findFragmentById(navHost) as NavHostFragment

    val navGraph = getOrCreateNavGraph(navHostFragment)

    val navFrag = this
    navFrag.navigateFragment()

    createDestinations(navGraph, activity, navHost)

    navFrag.startDestination?.let { navGraph.startDestination = it }

    navHostFragment.navController.graph = navGraph
    configTopLevelDestinations(activity, navHostFragment)

    cleanRouter(navFrag)
}

fun FlowNavFragmentRouter.navigateTo(destination: String, lifecycleOwner: LifecycleOwner) {
    getNavControllerByLifecycle(lifecycleOwner)?.apply {
        FlowNavApp.getEntryMap()[destination]
            ?.takeIf { it.actionId != -1 }
            ?.run { this@apply.navigate(actionId) }
    }
}

fun FlowNavFragmentRouter.navigateUp(lifecycleOwner: LifecycleOwner) =
    getNavControllerByLifecycle(lifecycleOwner)?.run {
        this.navigateUp()
    } ?: run {
        false
    }

private fun getOrCreateNavGraph(navHostFragment: NavHostFragment) =
    navHostFragment.navController.getGraphOr {
        NavGraph(NavGraphNavigator(NavigatorProvider()))
    }

private fun FlowNavFragmentRouter.createDestinations(
    navGraph: NavGraph,
    activity: FragmentActivity,
    navHost: Int
) {
    fragmentsToAdd.forEach { mapFragmentInfo ->
        navGraph.addDestination(
            FragmentNavigator(
                activity,
                activity.supportFragmentManager,
                navHost
            ).createDestination().apply {
                id = mapFragmentInfo.key
                className = mapFragmentInfo.value.className

                mapFragmentInfo.value.actions.forEach {
                    putAction(it.key, NavAction(it.value))
                }
            })
    }
}

private fun FlowNavFragmentRouter.configTopLevelDestinations(
    activity: FragmentActivity,
    navHostFragment: NavHostFragment
) {
    if (topLevelDestinations.isNotEmpty()) {
        AppBarConfiguration.Builder(topLevelDestinations.toSet()).build().apply {
            (activity as? AppCompatActivity)?.run {
                this.setupActionBarWithNavController(navHostFragment.navController, this@apply)
            }
        }
    }
}

private fun getNavControllerByLifecycle(lifecycleOwner: LifecycleOwner): NavController? {
    return when (lifecycleOwner) {
        is Fragment -> {
            lifecycleOwner.findNavController()
        }
        is FragmentActivity -> {
            lifecycleOwner.supportFragmentManager.fragments.first().findNavController()
        }
        else -> {
            error("Navigate only on Fragment or FragmentActivity")
        }
    }
}

internal fun cleanRouter(navigateFragment: FlowNavFragmentRouter) {
    navigateFragment.fragmentsToAdd.clear()
    navigateFragment.startDestination = null
    navigateFragment.topLevelDestinations.clear()
}
