package com.flownav.router.extension

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import java.lang.Exception

fun NavController.getGraphOr(block: () -> NavGraph) =
    try {
        this.graph
    } catch (ex: Exception) {
        block()
    }
