package com.example.trening

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
setSettings()
        findViewById<Button>(R.id.btnPushUps).setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("exercise_type", "Pompki")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnSitUps).setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("exercise_type", "Brzuszki")
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnHistory).setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setSettings()
    {
        val ownLayer = findViewById<ConstraintLayout>(R.id.linearLayout3)
        ownLayer.setBackgroundResource(android.R.color.darker_gray)

    }
}//cos wyjdzie.

//TODO: sprawdzic gita

