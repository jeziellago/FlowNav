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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/***
 * Call startActivity from [destinationKey] using [flags] to Intent and
 * [args] as Bundle params
 */
fun Context.start(
    destinationKey: String,
    flags: List<Int>? = null,
    args: IntentParams = {}
) {
    startActivity(createIntent(destinationKey, flags, args))
}

/***
 * Call startForResult from [destinationKey] using [flags] to Intent and
 * [args] as Bundle params
 */
fun Activity.startForResult(
    destinationKey: String,
    requestCode: Int,
    flags: List<Int>? = null,
    args: IntentParams = {}
) {
    startActivityForResult(createIntent(destinationKey, flags, args), requestCode)
}

/***
 * Create [Intent] from [destinationKey] using [flags] to Intent and
 * [args] as Bundle params
 */
fun Context.createIntent(
    destinationKey: String,
    flags: List<Int>? = null,
    args: IntentParams = {}
) = Intent(Intent.ACTION_VIEW)
    .putExtras(Bundle().apply(args))
    .setClassName(
        packageName,
        FlowNavApp.getEntryMap()[destinationKey]?.name
            ?: error("$destinationKey not found.")
    ).apply { flags?.forEach { addFlags(it) } }

typealias IntentParams = Bundle.() -> Unit
