package com.flownav.firstaction

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationRouter
import com.flownav.navigation.NavigationRoutes.FEATURE_1

@EntryFlowNav(FEATURE_1)
class FirstActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_action_activity)
        findViewById<Button>(R.id.go_to_second).setOnClickListener {
            NavigationRouter.openFeature2(this, "Argument from FIRST")
        }
    }
}
