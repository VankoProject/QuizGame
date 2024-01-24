package com.kliachenko.quizgame.progress

interface QuizRepository {

    fun loadData()

    class Base : QuizRepository {

        override fun loadData() {
            TODO("Not yet implemented")
        }
    }
}