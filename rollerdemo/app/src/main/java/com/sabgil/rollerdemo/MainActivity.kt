package com.sabgil.rollerdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sabgil.roller.RollerView
import com.sabgil.roller.models.rolling
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollerView = findViewById<RollerView>(R.id.rollerView)
        val rolling = rolling {
            images(
                this@MainActivity,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background
            )
        }
        startButton.setOnClickListener {
            rollerView.roll(rolling)
        }
    }
}
