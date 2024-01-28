package com.kliachenko.quizgame.progress

import androidx.lifecycle.ViewModel

class LoadViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val callback = object : LoadCallback {
        override fun loadedSuccessfully() {

        }

        override fun loadError(error: String) {

        }

    }
    fun load() {
        repository.loadData(callback)
    }


}