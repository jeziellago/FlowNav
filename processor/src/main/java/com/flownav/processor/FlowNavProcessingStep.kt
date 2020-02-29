package com.flownav.processor

import com.flownav.annotation.EntryFlowNav
import com.flownav.annotation.FlowNavMain
import com.google.auto.common.BasicAnnotationProcessor.ProcessingStep
import com.google.common.collect.SetMultimap
import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

internal class FlowNavProcessingStep(
    private val processingEnv: ProcessingEnvironment
) : ProcessingStep {

    override fun annotations(): MutableSet<out Class<out Annotation>> {
        return mutableSetOf(EntryFlowNav::class.java, FlowNavMain::class.java)
    }

    override fun process(
        elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>?
    ): MutableSet<Element> {

        val default = mutableSetOf<Element>()
        val kaptKotlinGeneratedDir = processingEnv.getKaptKotlinGeneratedDir() ?: return default
        val generatedNavPath = kaptKotlinGeneratedDir.getMainModule()
        val targetParentPath = "${generatedNavPath.first()}/$PROCESSOR_CACHE_PATH"

        val navPath = generatedNavPath.firstOrNull {
            kaptKotlinGeneratedDir.contains("/${it.split(PATH_SEPARATOR).last()}/")
        }

        if (navPath != null) {
            buildFlowNavMap(
                elementsByAnnotation,
                targetParentPath,
                navPath,
                kaptKotlinGeneratedDir
            )
        } else {
            createEntryFlowNavKeys(elementsByAnnotation, targetParentPath)
        }
        return default
    }

    private fun buildFlowNavMap(
        elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>?,
        targetParentPath: String,
        generatedNavPath: String,
        kaptKotlinGeneratedDir: String
    ) {
        val mainFlowNavInitializerElements = elementsByAnnotation?.run {
            this[FlowNavMain::class.java]
        } ?: return

        val generatedNavClass = File(
            "$generatedNavPath/${kaptKotlinGeneratedDir.getKaptDir()}",
            FLOW_NAV_GEN_FILE
        )

        val mainInitializer = mainFlowNavInitializerElements.first()
        val packageName = processingEnv.elementUtils.getPackageOf(mainInitializer).toString()
        val classBuilder = FlowNavActionsBuilder(generatedNavClass).openFile(packageName)

        File(targetParentPath).listFiles()?.forEach {
            classBuilder.addAction(it.name, it.readText())
        }
    }

    private fun createEntryFlowNavKeys(
        elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>?,
        targetParentPath: String
    ) {
        elementsByAnnotation?.get(EntryFlowNav::class.java)
            ?.forEach { element ->
                val action = element.getAnnotation(EntryFlowNav::class.java).actionName
                val fragmentId = element.getAnnotation(EntryFlowNav::class.java).actionId
                val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                if (element.kind.isClass) {
                    val className = element.simpleName.toString()
                    FlowNavActionsBuilder.writeTarget(
                        targetParentPath,
                        action,
                        "$packageName.$className",
                        fragmentId
                    )
                }
            }
    }
}
