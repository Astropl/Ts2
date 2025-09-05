package com.example.trening

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ExerciseActivity : AppCompatActivity() {

    private lateinit var tvExerciseTitle: TextView
    private lateinit var btnCounter: Button
    private lateinit var tvTimer: TextView
    //private lateinit var dbHelper: DBHelper

    private var currentReps = 5
    private var seriesLeft = 5
    private var countDownTimer = 4000 //w mili sek
    private var isResting = false
    private var restTimer: CountDownTimer? = null

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        println(findViewById<TextView>(R.id.tvExerciseTitle))
setSettings()
        dbHelper = DBHelper(this)
        //val exerciseType = tvExerciseTitle.text.toString()
        tvExerciseTitle = findViewById(R.id.tvExerciseTitle)
        btnCounter = findViewById(R.id.btnCounter)
        tvTimer = findViewById(R.id.tvTimer)
        //btnCounter = findViewById(R.id.btnCounter)
        btnCounter.textSize = 32f

        val exerciseType = intent.getStringExtra("exercise_type") ?: "Ćwiczenie"
        tvExerciseTitle.text = exerciseType
        tvExerciseTitle.text = exerciseType

        startNewSeries()

        btnCounter.setOnClickListener {
            if (!isResting) {
                currentReps--
                if (currentReps > 0) {
                    btnCounter.text = currentReps.toString()
                } else {
                    // Zapisujemy dane po zakończeniu serii
                    val success = dbHelper.insertWorkout(exerciseType, currentReps, seriesLeft)
                    if (success) {
                        Toast.makeText(this, "Zapisano do DB", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Błąd przy zapisie do bazy!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    seriesLeft--
                    if (seriesLeft > 0) {
                        startRestTimer()
                    } else {
                        finishWorkout()
                    }
                }
            }
        }
    }

private fun setSettings()
{
    val ownLayer = findViewById<ConstraintLayout>(R.id.linearLayoutExcersise)
    ownLayer.setBackgroundResource(android.R.color.darker_gray)

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
        btnCounter = findViewById(R.id.btnCounter)
        btnCounter.textSize = 28f
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

//        val exerciseType = intent.getStringExtra("exercise_type") ?: "Ćwiczenie"
//        val totalRepsDone = 5 * 5 // np. powtórzenia * serie, albo liczby dynamicznie
//        dbHelper.insertWorkout(exerciseType, totalRepsDone, 5)
    }

    private fun gotoDB() {

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
