package com.sabgil.rollerdemo

import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sabgil.roller.RollerView
import com.sabgil.roller.dsl.rolling
import com.sabgil.roller.models.Orientation
import com.sabgil.roller.models.RollerEngineType
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val items = listOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollerView = findViewById<RollerView>(R.id.rollerView)


        upButton.setOnClickListener {
            rollerView.roll(setupRolling(Orientation.UP))
        }

        downButton.setOnClickListener {
            rollerView.roll(setupRolling(Orientation.DOWN))
        }

        leftButton.setOnClickListener {
            rollerView.roll(setupRolling(Orientation.LEFT))
        }

        rightButton.setOnClickListener {
            rollerView.roll(setupRolling(Orientation.RIGHT))
        }
        startButton.setOnClickListener {
            rollerView.start()
        }
    }

    private fun setupRolling(orient: Orientation) =
        rolling(this) {
            engine {
                duration = 3000L
                type = RollerEngineType.ACCELERATE_DECELERATE
                onRollingStart {
                    Toast.makeText(this@MainActivity, "start", Toast.LENGTH_SHORT).show()
                }
                onRollingEnd {
                    Toast.makeText(this@MainActivity, "end", Toast.LENGTH_SHORT).show()
                }
            }
            frame {
                orientation = orient
                width = 250
                height = 250
                framePaint = Paint().apply {
                    style = Paint.Style.STROKE
                    strokeWidth = 5.0f
                }
                lane {
                    targetIndex = Random.nextInt() % 12
                    numberOfCycle = 3
                    drawableIds(items)
                }
            }
        }
}
