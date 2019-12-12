package com.flownav.router

object FlowNavApp {
    private lateinit var intentMap: Map<String, String>
    private lateinit var fragmentMap: Map<String, Pair<String, Int>>

    fun start(
        intentMap: Map<String, String>,
        fragmentMp: Map<String, Pair<String, Int>>
    ) {
        this.intentMap = intentMap
        this.fragmentMap = fragmentMp
    }

    fun getIntentMap() = intentMap
    fun getFragmentMap() = fragmentMap
}