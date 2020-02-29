package com.flownav.sample.otherapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.flownav.navigation.NavigationKeys
import com.flownav.router.start

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.go_to_second).setOnClickListener {
            // redirect to "FEATURE 1"
            start(NavigationKeys.FEATURE_1)
        }
    }
}
