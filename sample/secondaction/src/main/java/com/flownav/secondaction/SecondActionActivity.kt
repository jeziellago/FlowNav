package com.flownav.secondaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationRouter
import com.flownav.navigation.NavigationRoutes.FEATURE_2
import com.flownav.navigation.SecondActionArgKeys
import kotlinx.android.synthetic.main.second_action_activity.*

@EntryFlowNav(FEATURE_2)
class SecondActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_action_activity)
        val text = "SECOND: ${intent.extras?.getString(SecondActionArgKeys.arg1)}"
        txtSecond.text = text

        buttonOpenThird.setOnClickListener {
            startActivity(NavigationRouter.openFeature3(this))
        }
    }
}
