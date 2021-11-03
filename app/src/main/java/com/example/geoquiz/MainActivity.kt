package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var tvQuestion: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var score = 0

    private var currentIndex = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnNext = findViewById(R.id.btn_next)
        btnPrev = findViewById(R.id.btn_prev)
        tvQuestion = findViewById(R.id.tv_question)

        tvQuestion.setOnClickListener { nextQuestion() }
        btnTrue.setOnClickListener { checkAnswer(true) }
        btnFalse.setOnClickListener { checkAnswer(false) }
        btnNext.setOnClickListener { nextQuestion() }
        btnPrev.setOnClickListener { prevQuestion() }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private fun nextQuestion() {
        currentIndex++
        if (currentIndex == questionBank.size) {
            showResult()
            currentIndex = 0
            return
        }
        updateQuestion()
    }

    private fun prevQuestion() {
        if (currentIndex == 0) currentIndex = questionBank.size
        currentIndex--
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionResId = questionBank[currentIndex].textResId
        tvQuestion.setText(questionResId)
        isButtonsEnabled(true)
    }

    private fun checkAnswer(answer: Boolean) {
        isButtonsEnabled(false)
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (answer == correctAnswer) {
            score++
            R.string.correct
        } else { R.string.incorrect }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun isButtonsEnabled(value: Boolean) {
        btnTrue.isEnabled = value
        btnFalse.isEnabled = value
    }

    private fun showResult() {
        val percent = 100 / questionBank.size * score
        val text = getString(R.string.you_result_is, percent)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}