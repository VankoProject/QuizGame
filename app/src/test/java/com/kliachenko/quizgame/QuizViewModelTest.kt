package com.kliachenko.quizgame

import com.kliachenko.quizgame.game.Choice
import com.kliachenko.quizgame.game.ChoiceUiState
import com.kliachenko.quizgame.game.QuestionAndChoices
import com.kliachenko.quizgame.game.QuizRepository
import com.kliachenko.quizgame.game.QuizViewModel
import com.kliachenko.quizgame.game.UiState
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuizViewModelTest {

    @Test
    fun correctTwice() {
        val viewModel = QuizViewModel(repository = FakeRepository())

        var actual: UiState = viewModel.init()
        var expected: UiState = UiState.Question(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "A"),
                ChoiceUiState.Question(value = "B"),
                ChoiceUiState.Question(value = "C"),
                ChoiceUiState.Question(value = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("A")
        expected = UiState.Answered(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "E"),
                ChoiceUiState.Question(value = "F"),
                ChoiceUiState.Question(value = "G"),
                ChoiceUiState.Question(value = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("G")
        expected = UiState.Last(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.GameOver
        assertEquals(expected, actual)
    }

    @Test
    fun correctThenIncorrect() {
        val viewModel = QuizViewModel(repository = FakeRepository())

        var actual: UiState = viewModel.init()
        var expected: UiState = UiState.Question(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "A"),
                ChoiceUiState.Question(value = "B"),
                ChoiceUiState.Question(value = "C"),
                ChoiceUiState.Question(value = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("A")
        expected = UiState.Answered(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "E"),
                ChoiceUiState.Question(value = "F"),
                ChoiceUiState.Question(value = "G"),
                ChoiceUiState.Question(value = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("E")
        expected = UiState.Last(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Incorrect,
                ChoiceUiState.NotChosen,
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.GameOver
        assertEquals(expected, actual)
    }

    @Test
    fun incorrectTwice() {
        val viewModel = QuizViewModel(repository = FakeRepository())

        var actual: UiState = viewModel.init()
        var expected: UiState = UiState.Question(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "A"),
                ChoiceUiState.Question(value = "B"),
                ChoiceUiState.Question(value = "C"),
                ChoiceUiState.Question(value = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("D")
        expected = UiState.Answered(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
                ChoiceUiState.Incorrect,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "E"),
                ChoiceUiState.Question(value = "F"),
                ChoiceUiState.Question(value = "G"),
                ChoiceUiState.Question(value = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("E")
        expected = UiState.Last(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Incorrect,
                ChoiceUiState.NotChosen,
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.GameOver
        assertEquals(expected, actual)
    }

    @Test
    fun incorrectThenCorrect() {
        val viewModel = QuizViewModel(repository = FakeRepository())

        var actual: UiState = viewModel.init()
        var expected: UiState = UiState.Question(
            question = "question1",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "A"),
                ChoiceUiState.Question(value = "B"),
                ChoiceUiState.Question(value = "C"),
                ChoiceUiState.Question(value = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("D")
        expected = UiState.Answered(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
                ChoiceUiState.Incorrect,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.Question(value = "E"),
                ChoiceUiState.Question(value = "F"),
                ChoiceUiState.Question(value = "G"),
                ChoiceUiState.Question(value = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("G")
        expected = UiState.Last(
            choices = listOf<ChoiceUiState>(
                ChoiceUiState.NotChosen,
                ChoiceUiState.NotChosen,
                ChoiceUiState.Correct,
                ChoiceUiState.NotChosen,
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.GameOver
        assertEquals(expected, actual)
    }
}

private class FakeRepository : QuizRepository {

    private val list = listOf(
        QuestionAndChoices(
            question = "question1", choices = listOf(
                Choice(value = "A", correct = true),
                Choice(value = "B", correct = false),
                Choice(value = "C", correct = false),
                Choice(value = "D", correct = false)
            )
        ),
        QuestionAndChoices(
            question = "question2", choices = listOf(
                Choice(value = "E", correct = false),
                Choice(value = "F", correct = false),
                Choice(value = "G", correct = true),
                Choice(value = "H", correct = false)
            )
        )
    )

    private var index = 0

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
        TODO("Not yet implemented")
    }
}