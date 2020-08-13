/*
 * Copyright 2019 Jeziel Lago - Alex Soares.
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

import java.io.File

const val PACKAGE_MARKER = "<package-name>"

class FlowNavActionsBuilder(
    private val file: File
) {

    @Synchronized
    fun openFile(packageName: String) = apply {
        file.apply {
            if (!exists()) {
                parentFile.mkdirs()
                writeText(template.replace(PACKAGE_MARKER, packageName))
            }
        }
    }

    private val template = """
        package com.flownav.router.extension
        
        import com.flownav.router.EntryConfig
        import android.app.Application
        import $PACKAGE_MARKER.R
        
        fun Application.getEntryMap(): Map<String, EntryConfig> = 
            HashMap<String, EntryConfig>()
                .apply {
                }//end
        
    """.trimIndent()

    @Synchronized
    fun addAction(
        action: String,
        actionType: String
    ) {
        val content = if (actionType.contains("*")) {
            val split: List<String> = actionType.split("*")
            file.readText().replace(
                "}//end",
                "\n\t\t\t${createEntryConfig(action, split.first(), split[1])}\n\t\t}//end"
            )
        } else {
            file.readText().replace(
                "}//end",
                "\n\t\t\t${createEntryConfig(action, actionType)}\n\t\t}//end"
            )
        }
        file.writeText(content)
    }

    private fun createEntryConfig(
        action: String,
        actionType: String,
        idReference: String? = null
    ): String = if (idReference != null) {
        "this[\"$action\"] = EntryConfig(${actionType}::class.java.name, R.id.${idReference})"
    } else {
        "this[\"$action\"] = EntryConfig(${actionType}::class.java.name)"
    }

    companion object {

        fun writeTarget(
            parentPath: String,
            action: String,
            actionType: String,
            fragmentId: String
        ) {
            require(!action.contains(PATH_SEPARATOR)) {
                "Invalid key: \"$action\". \"$PATH_SEPARATOR\" is an invalid token."
            }
            File(parentPath, action).apply {
                parentFile.mkdirs()

                var textToWrite = actionType
                textToWrite += if (fragmentId.isNotEmpty()) "*$fragmentId" else fragmentId

                writeText(textToWrite)
            }
        }
    }
}
