package com.example.tapcounterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var arcana: Int = 0
        var level: Int = 0
        var arcanaIncrement: Int = 1
        var levelupval: Int = 50
        var isBoostActive: Boolean = false
        val boostDurationMillis: Long = 30000 // 30 seconds



        val rune = findViewById<ImageButton>(R.id.runeButton)
        val levelup = findViewById<Button>(R.id.levelup)
        levelup.isEnabled = false
        val boostBtn = findViewById<Button>(R.id.boost)
        boostBtn.isEnabled = false
        val arcanaText = findViewById<TextView>(R.id.Arcana)
        val levelText = findViewById<TextView>(R.id.level)

        val handler = Handler()
        val boostRunnable = Runnable {
            // Remove the boost effect after the specified duration
            arcanaIncrement /= 5
            isBoostActive = false
            boostBtn.isEnabled = true
            Toast.makeText(this, "Boost effect ended", Toast.LENGTH_SHORT).show()
        }

        arcanaText.setText(getString(R.string.Arcana_text)+arcana)
        levelText.setText(getString(R.string.Level_text)+level)

        rune.setOnClickListener {
            arcana+=arcanaIncrement
            arcanaText.setText(getString(R.string.Arcana_text)+arcana)

            if (arcana >= levelupval) {
                // Enable the "Level Up" button
                levelup.isEnabled = true
            }
        }

        levelup.setOnClickListener {
            arcana -= levelupval
            level+=1
            arcanaIncrement+=1
            levelupval = (levelupval*1.5).toInt()

            boostBtn.isEnabled = true

            Toast.makeText(this, "level up!", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Arcana goes up to $arcanaIncrement", Toast.LENGTH_SHORT).show()

            arcanaText.setText(getString(R.string.Arcana_text)+arcana)
            levelText.setText(getString(R.string.Level_text)+level)
            if(arcana < levelupval){
                levelup.isEnabled = false
            }
        }

        boostBtn.setOnClickListener {
            if (!isBoostActive) {
                if (level >= 1) {
                    // Deduct one level and apply the boost
                    level -= 1
                    arcanaIncrement *= 5
                    isBoostActive = true
                    boostBtn.isEnabled = false
                    Toast.makeText(this, "Boost activated for 30 seconds!", Toast.LENGTH_SHORT).show()

                    // Schedule the boost effect to end after 30 seconds
                    handler.postDelayed(boostRunnable, boostDurationMillis)
                } else {
                    Toast.makeText(this, "Not enough levels to boost", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Boost is already active", Toast.LENGTH_SHORT).show()
            }
        }

    }
}