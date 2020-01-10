package com.flownav.thirdmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationFragmentRouter.initNavGraphOn
import com.flownav.navigation.NavigationFragmentRouter.isNavigateUp
import com.flownav.navigation.NavigationKeys

@EntryFlowNav(NavigationKeys.FEATURE_3)
class ActivityStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)

        initNavGraphOn(this, R.id.navhostMain)
    }

    override fun onSupportNavigateUp() = isNavigateUp(this)
}
