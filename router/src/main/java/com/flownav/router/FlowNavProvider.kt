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
package com.flownav.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlin.reflect.KClass

fun setupFlowNavFragmentFactory(fragmentFactory: FragmentFactory) {
    FlowNavProvider.setFragmentFactory(fragmentFactory)
}

fun setupFlowNavFragmentProvider(provider: (KClass<*>) -> Fragment) {
    FlowNavProvider.setFragmentProvider(provider)
}

internal object FlowNavProvider {

    private var provider: ((KClass<*>) -> Fragment)? = null
    private var fragmentFactory: FragmentFactory? = null

    fun setFragmentProvider(provider: (KClass<*>) -> Fragment) {
        this.provider = provider
    }

    fun setFragmentFactory(fragmentFactory: FragmentFactory) {
        this.fragmentFactory = fragmentFactory
    }

    fun getFragment(
        clazz: KClass<*>
    ): Fragment? {
        return provider?.invoke(clazz)
            ?: fragmentFactory?.instantiate(
                ClassLoader.getSystemClassLoader(),
                clazz.java.name
            )
    }
}
