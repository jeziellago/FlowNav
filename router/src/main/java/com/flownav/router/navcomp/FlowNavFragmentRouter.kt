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

package com.flownav.router.navcomp

import com.flownav.router.EntryConfig
import com.flownav.router.FlowNavApp

open class FlowNavFragmentRouter {

    internal val fragmentsToAdd = mutableMapOf<Int, FragmentNavInfo>()
    internal var startDestination: Int? = null
    internal var topLevelDestinations: MutableList<Int> = mutableListOf()

    fun addDestination(
        isStartDestination: Boolean = false,
        isTopLevelDestination: Boolean = false,
        destinationKey: String
    ) = FlowNavApp.getEntryMap()[destinationKey]
        ?.run {
            checkAsStartDestination(isStartDestination)
            addTopLevelDestination(isTopLevelDestination)
            createFragmentNavInfo()
        }

    infix fun FragmentNavInfo.withActions(destinationActions: HashMap<String, String>.() -> Unit) {
        fragmentsToAdd[id] = this.apply {
            val destinations: HashMap<String, String> = hashMapOf()
            destinations.destinationActions()

            if (destinations.isNotEmpty()) {
                val newActions: HashMap<Int, Int> = hashMapOf()

                destinations.forEach {
                    FlowNavApp.getEntryMap()[it.key]
                        ?.actionId
                        ?.takeIf { id -> id != -1 }
                        ?.let { source ->
                            FlowNavApp.getEntryMap()[it.value]
                                ?.actionId
                                ?.takeIf { id -> id != -1 }
                                ?.let { destination -> newActions.put(source, destination) }
                        }
                }

                actions.putAll(newActions)
            }
        }
    }

    private fun EntryConfig.createFragmentNavInfo() = FragmentNavInfo(
        actionId,
        actionName
    ).apply {
        fragmentsToAdd[id] = this
    }

    private fun EntryConfig.checkAsStartDestination(
        isStartDestination: Boolean
    ) {
        if (isStartDestination) {
            startDestination = actionId
        }
    }

    private fun EntryConfig.addTopLevelDestination(
        isTopLevelDestination: Boolean
    ) {
        if (isTopLevelDestination) {
            topLevelDestinations.add(actionId)
        }
    }
}
