package com.flownav.secondaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationKeys.FEATURE_2
import com.flownav.navigation.NavigationKeys.FEATURE_3
import com.flownav.navigation.SecondActionArgKeys
import com.flownav.router.startForResult
import kotlinx.android.synthetic.main.second_action_activity.*

@EntryFlowNav(FEATURE_2)
class SecondActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_action_activity)
        val text = "SECOND: ${intent.extras?.getString(SecondActionArgKeys.arg1)}"
        txtSecond.text = text

        buttonOpenThird.setOnClickListener {

            // call activity for result
            startForResult(FEATURE_3, 1)
        }
    }
}
