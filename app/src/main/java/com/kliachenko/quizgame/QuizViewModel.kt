package com.kliachenko.quizgame

class QuizViewModel(private val repository: QuizRepository) {


    fun init(): UiState {
        val data = repository.questionAndChoices()
        return UiState.Question(data.question, data.choices.map {
            ButtonChoiceUiState.Question(it.value)
        })
    }

    fun chooseAnswer(text: String): UiState {
        val data = repository.questionAndChoices()
        val choices = data.choices.map {
            when {
                it.correct -> ButtonChoiceUiState.Correct(it.value)
                text == it.value -> ButtonChoiceUiState.Incorrect(it.value)
                else -> ButtonChoiceUiState.NotChosen(it.value)
            }
        }
        return if (repository.isLastQuestion())
            UiState.Last(data.question, choices)
        else
            UiState.Answered(data.question, choices)
    }

    fun next(): UiState {
        return if (repository.isLastQuestion()) {
            UiState.GameOver
        } else {
            repository.next()
            init()
        }
    }
}
