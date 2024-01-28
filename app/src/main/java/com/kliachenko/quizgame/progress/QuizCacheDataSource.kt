package com.kliachenko.quizgame.progress

import com.google.gson.Gson
import com.kliachenko.quizgame.main.LocalStorage

interface QuizCacheDataSource {
    interface Save {
        fun save(data: QuizResponse)
    }

    interface Read {
        fun read(): List<QuizResult>
    }

    interface Mutable : Save, Read

    class Base(
        private val gson: Gson,
        private val localStorage: LocalStorage,
    ) : Mutable {
        override fun save(data: QuizResponse) {
            localStorage.save(gson.toJson(data), KEY)
        }

        override fun read(): List<QuizResult> {
            val default = QuizResponse(-1, emptyList())
            val response = localStorage.read(KEY, gson.toJson(default))
            val quizResponse: QuizResponse = gson.fromJson(response, QuizResponse::class.java)
            return quizResponse.results
        }

        companion object {
            private const val KEY = "QuizResponse"
        }
    }

}
