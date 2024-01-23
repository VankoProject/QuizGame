package com.kliachenko.quizgame

import android.app.Application

class QuizApp : Application() {

    private lateinit var viewModel: QuizViewModel

    fun viewModel() = viewModel
    override fun onCreate() {
        super.onCreate()
        viewModel =
            QuizViewModel(QuizRepository.Base(permanentStorage = PermanentStorage.Base(this)))
    }
}