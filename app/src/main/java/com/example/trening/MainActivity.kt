package com.example.trening

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}//cos wyjdzie.

//TODO: sprawdzic gita

