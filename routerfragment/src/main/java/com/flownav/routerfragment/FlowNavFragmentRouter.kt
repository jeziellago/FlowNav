/*
 * Copyright 2019, Alex Soares.
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

package com.flownav.routerfragment

open class FlowNavFragmentRouter {
    internal val fragmentsToAdd = mutableMapOf<Int, FragmentNavInfo>()
    internal var startDestination: Int = 0

    fun addDestination(
        isStartDestination: Boolean = false,
        id: Int,
        className: String
    ): FragmentNavInfo {
        if (isStartDestination) {
            startDestination = id
        }

        return FragmentNavInfo(
            id,
            className
        ).apply {
            fragmentsToAdd[id] = this
        }
    }

    infix fun FragmentNavInfo.withActions(destinationActions: HashMap<Int, Int>.() -> Unit) {
        fragmentsToAdd[id] = this.apply { actions.apply(destinationActions) }
    }
}
