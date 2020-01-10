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
    val actionName: String,
    val actionId: Int = -1
)
