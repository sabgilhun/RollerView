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

            focusFrame {
                width = 200
                height = 200

                circularLane {
                    images(
                        this@MainActivity,
                        R.drawable.roller_alligator_100,
                        R.drawable.roller_cat_100,
                        R.drawable.roller_corgi_100,
                        R.drawable.roller_dove_100,
                        R.drawable.roller_duck_100,
                        R.drawable.roller_elephant_100,
                        R.drawable.roller_german_shepherd_100,
                        R.drawable.roller_horse_100,
                        R.drawable.roller_octopus_100,
                        R.drawable.roller_reindeer_100,
                        R.drawable.roller_sheep_100,
                        R.drawable.roller_teddy_bear_100
                    )
                }
            }
        }

        startButton.setOnClickListener {
            rollerView.roll(rolling)
        }
    }
}
