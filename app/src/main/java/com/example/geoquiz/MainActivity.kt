package com.example.geoquiz

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private val vm: QuestionViewModel by lazy {
        ViewModelProviders.of(this).get(QuestionViewModel::class.java)
    }

    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var tvQuestion: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        vm.currentIndex = currentIndex

        btnTrue = findViewById(R.id.btn_true)
        btnFalse = findViewById(R.id.btn_false)
        btnNext = findViewById(R.id.btn_next)
        btnPrev = findViewById(R.id.btn_prev)
        tvQuestion = findViewById(R.id.tv_question)

        tvQuestion.setOnClickListener { vm.moveToNext() }
        btnTrue.setOnClickListener { checkAnswer(true) }
        btnFalse.setOnClickListener { checkAnswer(false) }
        btnNext.setOnClickListener {
            vm.moveToNext()
            updateQuestion()
        }
        btnPrev.setOnClickListener {
            vm.moveToPrev()
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        System.out.println("OnSaveInstanceState")
        outState.putInt(KEY_INDEX, vm.currentIndex)
    }

    private fun updateQuestion() {
        val questionResId = vm.currentQuestionTextId
        tvQuestion.setText(questionResId)
        isButtonsEnabled(true)
    }

    private fun checkAnswer(answer: Boolean) {
        isButtonsEnabled(false)
        val correctAnswer = vm.currentQuestionAnswer
        val messageResId = if (answer == correctAnswer) {
            R.string.correct
        } else {
            R.string.incorrect
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun isButtonsEnabled(value: Boolean) {
        btnTrue.isEnabled = value
        btnFalse.isEnabled = value
    }

    override fun onPause() {
        super.onPause()
        System.out.println("onPause")
    }

    override fun onStop() {
        super.onStop()
        System.out.println("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("onDestroy")
    }
}