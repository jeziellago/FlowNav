@file:Suppress("SpellCheckingInspection")

package com.flownav.processor

import java.io.File
import java.io.FileInputStream
import java.lang.IllegalArgumentException
import java.util.Properties
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic

internal const val INCREMENTAL_ISOLATING = "org.gradle.annotation.processing.isolating"
internal const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
internal const val PROCESSOR_CACHE_PATH = "build/generated/source/cache/flownav-processor"
internal const val FLOW_NAV_GEN_FILE = "FlowNavExtKt.kt"
internal const val DEFAULT_MAIN_MODULE = "app"
internal const val PROPERTIES_FILE = "gradle.properties"
internal const val FLOWNAV_MAIN_MODULE_PROPERTY = "flownav.main.module"
internal const val BUILD_TYPE_SOURCE = "build/generated/source"
internal const val PATH_SEPARATOR = "/"
internal const val APP_MODULE_SEPARATOR = ","
internal const val APP_MODULE_MARKER = "_MARKER"

internal fun ProcessingEnvironment.getKaptKotlinGeneratedDir(): String? {
    return options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
        messager.printMessage(
            Diagnostic.Kind.ERROR,
            "Can't find the target directory for generated Kotlin files."
        )
        null
    }
}

internal fun String.getMainModule(): List<String> {
    val source: String = this
    val propertyFilePath = findTargetPath(PROPERTIES_FILE)

    fun validateModule(path: String, targetModule: String): String {
        if (File(path).exists().not()) {
            throw IllegalArgumentException(
                "'$targetModule' module not found. " +
                        "Check the property 'flownav.main.module'" +
                        " on gradle.properties."
            )
        }
        return path
    }

    val mainDefaultModules: (String) -> List<String> = { target ->
        val targetAppModules = target.split(APP_MODULE_SEPARATOR)
        if (targetAppModules.isEmpty()) {
            val mainModule = source.findTargetPath(target)
            listOf(validateModule(mainModule, target))
        } else {
            targetAppModules.map {
                val mainModule = source.findTargetPath(it)
                validateModule(mainModule, it)
            }
        }
    }

    return if (propertyFilePath.isNotEmpty()) {
        Properties()
            .apply { load(FileInputStream(propertyFilePath)) }
            .run {
                getProperty(FLOWNAV_MAIN_MODULE_PROPERTY, null)
                    ?.let { mainDefaultModules.invoke(it.trim()) }
            } ?: mainDefaultModules.invoke(DEFAULT_MAIN_MODULE)
    } else {
        mainDefaultModules(DEFAULT_MAIN_MODULE)
    }
}

internal tailrec fun String.findTargetPath(
    target: String
): String {
    if (isEmpty()) return ""
    return File(this)
        .listFiles()
        ?.firstOrNull { it.name == target }
        ?.absolutePath
        ?: substringBeforeLast(PATH_SEPARATOR, "")
            .findTargetPath(target)
}

internal fun String.getKaptDir(): String {
    val splitted = split(PATH_SEPARATOR)
    var buildTypeDir = BUILD_TYPE_SOURCE
    splitted.subList(splitted.lastIndexOf("kaptKotlin"), splitted.size)
        .forEach { buildTypeDir += "$PATH_SEPARATOR$it" }
    return buildTypeDir
}
