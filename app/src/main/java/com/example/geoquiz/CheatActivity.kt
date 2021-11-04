package com.example.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

private const val TRUE_ANSWER_KEY = "com.example.CheatActivity.TRUE_ANSWER_KEY"
const val IS_ANSWER_SHOWED = "com.example.CheatActivity.IS_ANSWER_SHOWED"

class CheatActivity : AppCompatActivity() {

    private lateinit var tvAnswer: TextView
    private lateinit var btnShowAnswer: Button
    private var trueAnswer: Boolean = false
    private var isAnswerShowed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        isAnswerShowed = savedInstanceState?.getBoolean(IS_ANSWER_SHOWED, false) ?: false
        trueAnswer = intent.getBooleanExtra(TRUE_ANSWER_KEY, false)
        setActivityResult()

        tvAnswer = findViewById(R.id.tv_answer)
        btnShowAnswer = findViewById(R.id.btn_show_answer)

        btnShowAnswer.setOnClickListener {
            isAnswerShowed = true
            val answer = if (trueAnswer) R.string.text_true else R.string.text_false
            tvAnswer.setText(answer)
            setActivityResult()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_ANSWER_SHOWED, isAnswerShowed)
    }

    private fun setActivityResult() {
        val data = Intent().apply {
            putExtra(IS_ANSWER_SHOWED, isAnswerShowed)
        }
        setResult(RESULT_OK, data)
    }

    companion object {
        fun newIntent(context: Context, trueAnswer: Boolean): Intent {
            return Intent(context, CheatActivity::class.java).apply {
                putExtra(TRUE_ANSWER_KEY, trueAnswer)
            }
        }
    }
}