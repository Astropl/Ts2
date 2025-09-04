package com.example.trening

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExerciseActivity : AppCompatActivity() {

    private lateinit var tvExerciseTitle: TextView
    private lateinit var btnCounter: Button
    private lateinit var tvTimer: TextView

    private var currentReps = 5
    private var seriesLeft = 5
    private var countDownTimer = 10000 //w mili sek
    private var isResting = false
    private var restTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        tvExerciseTitle = findViewById(R.id.tvExerciseTitle)
        btnCounter = findViewById(R.id.btnCounter)
        tvTimer = findViewById(R.id.tvTimer)

        // Odbieramy typ ćwiczenia z Intentu
        val exerciseType = intent.getStringExtra("exercise_type") ?: "Ćwiczenie"
        tvExerciseTitle.text = exerciseType

        startNewSeries()

        btnCounter.setOnClickListener {
            if (!isResting) {
                currentReps--
                if (currentReps > 0) {
                    btnCounter.text = currentReps.toString()
                } else {
                    seriesLeft--
                    if (seriesLeft > 0) {
                        startRestTimer()
                    } else {
                        finishWorkout()
//                        btnCounter.text = "Koniec!"
//                        btnCounter.isEnabled = false
                    }
                }
            }
        }
    }

    private fun startNewSeries() {
        currentReps = seriesLeft
        btnCounter.text = currentReps.toString()
        animateButtonBackground(R.drawable.circle_button_active)
        // 🔵 seria
        tvTimer.text = ""
        isResting = false
    }

    private fun startRestTimer() {
        isResting = true
        btnCounter.text = "Odpoczynek"
        animateButtonBackground(R.drawable.circle_button_rest)

        //btnCounter.setBackgroundResource(R.drawable.circle_button_rest) // ⚪ odpoczynek
        //tvTimer.text = "60"
        tvTimer.text = countDownTimer.toString()
        restTimer = object : CountDownTimer(countDownTimer.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                tvTimer.text = seconds.toString()
            }

            override fun onFinish() {
                startNewSeries()
            }
        }.start()
    }
    private fun finishWorkout() {
        btnCounter.text = "Koniec!"
        animateButtonBackground(R.drawable.circle_button_finish)
        //btnCounter.setBackgroundResource(R.drawable.circle_button_finish) // 🟢 zakończone
        btnCounter.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        restTimer?.cancel()
    }

    private fun animateButtonBackground(newDrawable: Int) {
        val anim = ObjectAnimator.ofFloat(btnCounter, "alpha", 0f, 1f)
        anim.duration = 300 // 0.3 sekundy
        btnCounter.setBackgroundResource(newDrawable)
        anim.start()
    }

}
