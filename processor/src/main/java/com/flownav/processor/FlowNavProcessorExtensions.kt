@file:Suppress("SpellCheckingInspection")

package com.flownav.processor

import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic

internal const val INCREMENTAL_ISOLATING = "org.gradle.annotation.processing.isolating"
internal const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
internal const val PROCESSOR_CACHE_PATH = "build/generated/source/cache/flownav-processor"
internal const val SLASHED_APP_DIR = "/app/"
internal const val FLOW_NAV_GEN_FILE = "FlowNavExtKt.kt"

internal fun ProcessingEnvironment.getKaptKotlinGeneratedDir(): String? {
    return options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
        messager.printMessage(
            Diagnostic.Kind.ERROR,
            "Can't find the target directory for generated Kotlin files."
        )
        null
    }
}

internal tailrec fun String.getModulePath(targetModule: String = "app"): String {
    File(this).listFiles()?.forEach {
        if (it.isDirectory && it.name == targetModule) {
            return it.absolutePath
        }
    }
    return substringBeforeLast("/").getModulePath(targetModule)
}

internal fun String.getKaptDir(): String {
    val splitted = split("/")
    var buildTypeDir = "build/generated/source"
    splitted.subList(splitted.lastIndexOf("kaptKotlin"), splitted.size)
        .forEach { buildTypeDir += "/$it" }
    return buildTypeDir
}
