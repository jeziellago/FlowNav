package com.flownav.router

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner

fun LifecycleOwner.startFragment(
    @IdRes container: Int,
    destinationKey: String,
    addToBackStack: Boolean = false,
    commitNow: Boolean = false,
    tag: String? = null,
    args: Bundle.() -> Unit = {}
) {
    loadFragment(container, destinationKey, false, addToBackStack, commitNow, tag, args)
}

fun LifecycleOwner.startFragmentByReplace(
    @IdRes container: Int,
    destinationKey: String,
    addToBackStack: Boolean = false,
    commitNow: Boolean = false,
    tag: String? = null,
    args: Bundle.() -> Unit
) {
    loadFragment(container, destinationKey, true, addToBackStack, commitNow, tag, args)
}

fun LifecycleOwner.createFragment(
    @IdRes container: Int,
    destinationKey: String,
    replace: Boolean = false,
    args: Bundle.() -> Unit
): FragmentTransaction {
    val fragmentIdentifier = FlowNavApp.getEntryMap()[destinationKey]
        ?: error("$destinationKey not found.")

    val fragment: Fragment = Class.forName(fragmentIdentifier.actionName)
        .newInstance() as? Fragment
        ?: error("$destinationKey must be a Fragment.")

    fragment.arguments = Bundle().apply(args)

    val fragmentManager = when (this) {
        is FragmentActivity -> supportFragmentManager
        is Fragment -> childFragmentManager
        else -> error("Class must be Fragment or Activity.")
    }

    return fragmentManager.beginTransaction()
        .apply { if (replace) replace(container, fragment) else add(container, fragment) }
}

private fun LifecycleOwner.loadFragment(
    @IdRes container: Int,
    destinationKey: String,
    replace: Boolean = false,
    addToBackStack: Boolean = true,
    commitNow: Boolean = false,
    tag: String? = null,
    args: Bundle.() -> Unit
) {
    createFragment(container, destinationKey, replace, args)
        .apply { if (addToBackStack) addToBackStack(tag ?: destinationKey) }
        .apply { if (commitNow) commitNow() else commit() }
}
