package com.example.geoquiz

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"
private const val REQUEST_CHEAT_CODE = 0

class MainActivity : AppCompatActivity() {

    private val vm: QuestionViewModel by lazy {
        ViewModelProviders.of(this).get(QuestionViewModel::class.java)
    }

    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnNext: ImageButton
    private lateinit var btnPrev: ImageButton
    private lateinit var btnCheat: Button
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
        btnCheat = findViewById(R.id.btn_cheat)
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
        btnCheat.setOnClickListener { view -> openCheatActivity(view) }
        updateQuestion()
    }

    private fun openCheatActivity(view: View) {
        if (vm.cheatCount <= 0) {
            btnCheat.isEnabled = false
            return
        }
        val intent = CheatActivity.newIntent(this, vm.currentQuestionAnswer)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val options = ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
            startActivityForResult(intent, REQUEST_CHEAT_CODE, options.toBundle())
        } else {
            startActivityForResult(intent, REQUEST_CHEAT_CODE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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
        val messageResId = when {
            vm.isCheater -> R.string.judgment_toast
            (answer == correctAnswer) -> R.string.correct
            else -> R.string.incorrect
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun isButtonsEnabled(value: Boolean) {
        btnTrue.isEnabled = value
        btnFalse.isEnabled = value
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHEAT_CODE && resultCode == RESULT_OK) {
            vm.isCheater = data?.getBooleanExtra(IS_ANSWER_SHOWED, false) ?: false
            if (vm.isCheater) {
                vm.cheatCount--
            }
        }
    }
}