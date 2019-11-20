/*
 * Copyright 2019 Jeziel Lago.
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
        package $PACKAGE_MARKER
        
        object FlowNavActions {
            private val map by lazy {
                HashMap<String, String>().apply {
                }//endmap
            }
            fun get(): Map<String, String> = map
        }
    """.trimIndent()

    @Synchronized
    fun addAction(
        action: String,
        actionType: String
    ) {
        val content = file.readText().replace(
            "}//endmap",
            "\n\t\t\tthis[\"$action\"] = \"$actionType\"\n\t\t}//endmap"
        )
        file.writeText(content)
    }

    companion object {

        fun writeTarget(
            parentPath: String,
            action: String,
            actionType: String
        ) {
            File(parentPath, action).apply {
                parentFile.mkdirs()
                writeText("$actionType")
            }
        }
    }
}