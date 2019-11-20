/*
 * Copyright 2019, Jeziel Lago.
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
package com.flownav.processor

import com.flownav.annotation.EntryFlowNav
import com.flownav.annotation.FlowNavMain
import com.google.auto.service.AutoService
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
@SupportedAnnotationTypes(
    "com.flownav.annotation.EntryFlowNav",
    "com.flownav.annotation.FlowNavMain"
)
@SupportedOptions(FlowNavProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class FlowNavProcessor : AbstractProcessor() {

    override fun process(
        elementSet: MutableSet<out TypeElement>,
        environment: RoundEnvironment
    ): Boolean {

        val kaptKotlinGeneratedDir =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "Can't find the target directory for generated Kotlin files."
                )
                return false
            }

        val generatedNavPath = getModulePath(kaptKotlinGeneratedDir, "app")
        val targetParentPath = "$generatedNavPath/build/generated/source/cache/flownav-processor"

        if (kaptKotlinGeneratedDir.contains("/app/")) {
            val mainFlowNavInitializerElements = environment.getElementsAnnotatedWith(FlowNavMain::class.java)
            val generatedNavClass = File("$generatedNavPath/${getKaptDir(kaptKotlinGeneratedDir)}", "FlowNavActions.kt")
            if (generatedNavClass.exists()) return true
            val mainInitializer = mainFlowNavInitializerElements.first()
            val packageName = processingEnv.elementUtils.getPackageOf(mainInitializer).toString()
            val classBuilder = FlowNavActionsBuilder(generatedNavClass).openFile(packageName)
            File(targetParentPath).listFiles()?.forEach {
                if (!it.name.contains(".kt")) {
                    classBuilder.addAction(it.name, it.readText())
                }
            }
            return true
        }

        val annotatedElements = environment.getElementsAnnotatedWith(EntryFlowNav::class.java)

        annotatedElements
            .forEach { element ->
                val action = element.getAnnotation(EntryFlowNav::class.java).actionName
                val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                if (element.kind.isClass) {
                    val className = element.simpleName.toString()
                    FlowNavActionsBuilder.writeTarget(
                        targetParentPath,
                        action,
                        "$packageName.$className"
                    )
                }
            }
        return true
    }

    private tailrec fun getModulePath(currentPath: String, targetModule: String): String {
        File(currentPath).listFiles()?.forEach {
            if (it.isDirectory && it.name == targetModule) {
                return it.absolutePath
            }
        }
        return getModulePath(currentPath.substringBeforeLast("/"), targetModule)
    }

    private fun getKaptDir(currentPath: String): String {
        val splitted = currentPath.split("/")
        var buildTypeDir = "build/generated/source"
        splitted.subList(splitted.lastIndexOf("kaptKotlin"), splitted.size)
            .forEach { buildTypeDir += "/$it" }
        return buildTypeDir
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}