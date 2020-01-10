package com.flownav.router.extension

import android.content.Context

fun <K, V> Context.getEntryMap(): Map<K, V> {
    throw Throwable("This implementation should be generated at compile time.")
}
