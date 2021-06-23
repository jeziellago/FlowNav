package com.flownav.secondaction

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.flownav.annotation.EntryFlowNav
import com.flownav.navigation.NavigationKeys.FEATURE_2
import com.flownav.navigation.NavigationKeys.FEATURE_3
import com.flownav.navigation.SecondActionArgKeys
import com.flownav.router.startForResult

@EntryFlowNav(FEATURE_2)
class SecondActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_action_activity)
        val text = "SECOND: ${intent.extras?.getString(SecondActionArgKeys.arg1)}"
        findViewById<TextView>(R.id.txtSecond).text = text

        findViewById<Button>(R.id.buttonOpenThird).setOnClickListener {

            // call activity for result
            startForResult(FEATURE_3, 1)
        }
    }
}
