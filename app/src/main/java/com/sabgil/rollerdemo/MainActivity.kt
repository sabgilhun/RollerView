package com.sabgil.rollerdemo

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sabgil.roller.RollerView
import com.sabgil.roller.dsl.rolling
import com.sabgil.roller.models.Orientation
import com.sabgil.roller.models.RollerEngineType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollerView = findViewById<RollerView>(R.id.rollerView)
        val items = listOf(
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
        val rolling = rolling(this) {

            engine {
                duration = 5000L
                type = RollerEngineType.NORMAL
                onRollingStart = {
                    Toast.makeText(this@MainActivity, "start", Toast.LENGTH_SHORT).show()
                }
                onRollingEnd = {
                    Toast.makeText(this@MainActivity, "end", Toast.LENGTH_SHORT).show()
                }
            }

            frame {
                orientation = Orientation.LEFT
                width = 150
                height = 150
                framePaint = Paint().apply {
                    color = Color.DKGRAY
                    style = Paint.Style.STROKE
                    strokeWidth = 20f
                }

                lane {
                    targetIndex = 0
                    numberOfCycle = 2
                    drawableIds(items)
                }
            }
        }

        rollerView.roll(rolling)

        startButton.setOnClickListener {
            rollerView.start()
        }
    }
}
