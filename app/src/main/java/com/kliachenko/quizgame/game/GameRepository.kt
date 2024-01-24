package com.kliachenko.quizgame.game

import com.kliachenko.quizgame.main.LocalStorage

interface GameRepository {

    fun next()

    fun questionAndChoices(): QuestionAndChoices

    fun isLastQuestion(): Boolean

    fun finishGame()

    fun save()

    class Base(
        private val permanentStorage: PermanentStorage
    ) : GameRepository {

        private val list = listOf(
            QuestionAndChoices(
                question = "What color is christmas tree?", choices = listOf(
                    Choice(value = "green", correct = true),
                    Choice(value = "yellow", correct = false),
                    Choice(value = "red", correct = false),
                    Choice(value = "blue", correct = false)
                )
            ),
            QuestionAndChoices(
                question = "What color is milk?", choices = listOf(
                    Choice(value = "green", correct = false),
                    Choice(value = "white", correct = true),
                    Choice(value = "red", correct = false),
                    Choice(value = "blue", correct = false)
                )
            )
        )

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

        override fun finishGame() {
            index = 0
        }

        override fun save() {
            permanentStorage.saveIndex(index)
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