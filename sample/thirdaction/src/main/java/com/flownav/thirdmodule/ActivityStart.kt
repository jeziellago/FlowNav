package com.flownav.thirdmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationRoutes

@EntryFlowNav(NavigationRoutes.FEATURE_3)
class ActivityStart : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}
