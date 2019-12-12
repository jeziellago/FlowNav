package com.flownav.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.flownav.navigation.NavigationRouter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.go_to_second).setOnClickListener {
            NavigationRouter.openFeature1(this)
        }
    }
}
