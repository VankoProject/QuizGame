package com.kliachenko.quizgame.game

import com.kliachenko.quizgame.main.LocalStorage
import com.kliachenko.quizgame.progress.QuizCacheDataSource

interface GameRepository : FinishGame{

    fun next()

    fun questionAndChoices(): QuestionAndChoices

    fun isLastQuestion(): Boolean

    fun save()

    class Base(
        quizCacheDataSource: QuizCacheDataSource.Read,
        private val permanentStorage: PermanentStorage,
    ) : GameRepository {

        private val list = quizCacheDataSource.read().map {
            val choices = mutableListOf<Choice>()
            choices.add(Choice(it.answer, true))
            choices.addAll(
                it.incorrectChoices.map { incorrect ->
                    Choice(incorrect, false)
                }
            )
            choices.shuffle()
            QuestionAndChoices(it.question, choices)
        }

        private var index = permanentStorage.index()

        override fun next() {
            index++
        }

        override fun questionAndChoices(): QuestionAndChoices {
            return list[index]
        }

        override fun isLastQuestion(): Boolean {
            return index == list.size - 1
        }

        override fun save() {
            permanentStorage.saveIndex(index)
        }

        override fun finish() {
            index = 0
        }
    }
}

data class QuestionAndChoices(val question: String, val choices: List<Choice>)

data class Choice(val value: String, val correct: Boolean)

interface PermanentStorage {

    fun index(): Int

    fun saveIndex(index: Int)

    class Base(private val localStorage: LocalStorage) : PermanentStorage {


        override fun index(): Int {
            return localStorage.read("index", 0)
        }

        override fun saveIndex(index: Int) {
            localStorage.save(index, "index")
        }
    }
}