package com.flownav.router

object FlowNavApp {
    private lateinit var activityMap: Map<String, String>
    private lateinit var fragmentMap: Map<String, FragmentConfig>

    fun start(
        activityMap: Map<String, String> = emptyMap(),
        fragmentMap: Map<String, FragmentConfig> = emptyMap()
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

data class FragmentConfig(
    val actionName: String,
    val fragmentId: Int
)
