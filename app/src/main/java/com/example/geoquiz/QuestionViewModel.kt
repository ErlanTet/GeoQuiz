package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {

    var isCheater: Boolean = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    var currentIndex = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionTextId: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        isCheater = false
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        if (currentIndex == 0) currentIndex = questionBank.size
        currentIndex--
    }
}