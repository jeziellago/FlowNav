package com.flownav.router

object FlowNavApp {

    private lateinit var entryMap: Map<String, EntryConfig>

    fun start(
        entryMap: Map<String, EntryConfig> = emptyMap()
    ) {
        require(entryMap.isNotEmpty()) { "entryMap must be required for start FlowNavApp." }
        this.entryMap = entryMap
    }

    fun getEntryMap() = entryMap
}

data class EntryConfig(
    val name: String,
    val id: Int = -1
)

internal fun Int.isNotDefaultId(): Boolean = (this != -1)
