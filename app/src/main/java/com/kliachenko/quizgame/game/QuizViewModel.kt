package com.kliachenko.quizgame.game

import androidx.lifecycle.ViewModel
import com.kliachenko.quizgame.main.Navigate
import com.kliachenko.quizgame.main.ScreenRepository
import com.kliachenko.quizgame.progress.LoadScreen

class QuizViewModel(
    private val repository: GameRepository,
    private val screenRepository: ScreenRepository.Save,
    private val navigate: Navigate,
) : ViewModel(), FinishGame {

    fun init(): UiState {
        val data = repository.questionAndChoices()
        return UiState.Question(data.question, data.choices.map {
            ChoiceUiState.Question(it.value)
        })
    }

    fun choose(text: String): UiState {
        val data = repository.questionAndChoices()
        val choices = data.choices.map {
            when {
                it.correct -> ChoiceUiState.Correct
                text == it.value -> ChoiceUiState.Incorrect
                else -> ChoiceUiState.NotChosen
            }
        }
        return if (repository.isLastQuestion())
            UiState.Last(choices)
        else
            UiState.Answered(choices)
    }

    fun next(): UiState {
        return if (repository.isLastQuestion()) {
            repository.finish()
            UiState.GameOver
        } else {
            repository.next()
            init()
        }
    }

    fun save() {
        repository.save()
    }

    override fun finish() {
        repository.finish()
        screenRepository.saveShouldLoadNewGame()
        navigate.navigate(LoadScreen)
    }
}