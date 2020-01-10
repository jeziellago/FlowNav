package com.flownav.firstaction

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationKeys.FEATURE_1
import com.flownav.navigation.NavigationKeys.FEATURE_2
import com.flownav.navigation.SecondActionArgKeys
import com.flownav.router.start

@EntryFlowNav(FEATURE_1)
class FirstActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_action_activity)
        findViewById<Button>(R.id.go_to_second).setOnClickListener {

            // redirect to "FEATURE 2" using params
            start(FEATURE_2) {
                putString(SecondActionArgKeys.arg1, "Argument from FIRST")
            }
        }
    }
}
