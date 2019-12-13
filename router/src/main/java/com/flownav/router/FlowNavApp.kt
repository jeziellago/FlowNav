package com.flownav.router

object FlowNavApp {
    private lateinit var activityMap: Map<String, String>
    private lateinit var fragmentMap: Map<String, Pair<String, Int>>

    fun start(
        activityMap: Map<String, String> = emptyMap(),
        fragmentMap: Map<String, Pair<String, Int>> = emptyMap()
    ) {
        require(!(activityMap.isEmpty() && fragmentMap.isEmpty())) {
            "activityMap or fragmentMap must be required for start FlowNavApp."
        }
        this.activityMap = activityMap
        this.fragmentMap = fragmentMap
    }

    fun getActivityMap() = activityMap
    fun getFragmentMap() = fragmentMap
}