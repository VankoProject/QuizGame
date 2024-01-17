package com.kliachenko.quizgame

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
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "A"),
                ButtonChoiceUiState.Question(text = "B"),
                ButtonChoiceUiState.Question(text = "C"),
                ButtonChoiceUiState.Question(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("A")
        expected = UiState.Answered(
            question = "question1",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Correct(text = "A"),
                ButtonChoiceUiState.NotChosen(text = "B"),
                ButtonChoiceUiState.NotChosen(text = "C"),
                ButtonChoiceUiState.NotChosen(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "E"),
                ButtonChoiceUiState.Question(text = "F"),
                ButtonChoiceUiState.Question(text = "G"),
                ButtonChoiceUiState.Question(text = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("G")
        expected = UiState.Last(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.NotChosen(text = "E"),
                ButtonChoiceUiState.NotChosen(text = "F"),
                ButtonChoiceUiState.Correct(text = "G"),
                ButtonChoiceUiState.NotChosen(text = "H"),
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
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "A"),
                ButtonChoiceUiState.Question(text = "B"),
                ButtonChoiceUiState.Question(text = "C"),
                ButtonChoiceUiState.Question(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("A")
        expected = UiState.Answered(
            question = "question1",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Correct(text = "A"),
                ButtonChoiceUiState.NotChosen(text = "B"),
                ButtonChoiceUiState.NotChosen(text = "C"),
                ButtonChoiceUiState.NotChosen(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "E"),
                ButtonChoiceUiState.Question(text = "F"),
                ButtonChoiceUiState.Question(text = "G"),
                ButtonChoiceUiState.Question(text = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("E")
        expected = UiState.Last(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Incorrect(text = "E"),
                ButtonChoiceUiState.NotChosen(text = "F"),
                ButtonChoiceUiState.Correct(text = "G"),
                ButtonChoiceUiState.NotChosen(text = "H"),
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
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "A"),
                ButtonChoiceUiState.Question(text = "B"),
                ButtonChoiceUiState.Question(text = "C"),
                ButtonChoiceUiState.Question(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("D")
        expected = UiState.Answered(
            question = "question1",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Correct(text = "A"),
                ButtonChoiceUiState.NotChosen(text = "B"),
                ButtonChoiceUiState.NotChosen(text = "C"),
                ButtonChoiceUiState.Incorrect(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "E"),
                ButtonChoiceUiState.Question(text = "F"),
                ButtonChoiceUiState.Question(text = "G"),
                ButtonChoiceUiState.Question(text = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("E")
        expected = UiState.Last(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Incorrect(text = "E"),
                ButtonChoiceUiState.NotChosen(text = "F"),
                ButtonChoiceUiState.Correct(text = "G"),
                ButtonChoiceUiState.NotChosen(text = "H"),
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
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "A"),
                ButtonChoiceUiState.Question(text = "B"),
                ButtonChoiceUiState.Question(text = "C"),
                ButtonChoiceUiState.Question(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("D")
        expected = UiState.Answered(
            question = "question1",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Correct(text = "A"),
                ButtonChoiceUiState.NotChosen(text = "B"),
                ButtonChoiceUiState.NotChosen(text = "C"),
                ButtonChoiceUiState.Incorrect(text = "D"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = UiState.Question(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.Question(text = "E"),
                ButtonChoiceUiState.Question(text = "F"),
                ButtonChoiceUiState.Question(text = "G"),
                ButtonChoiceUiState.Question(text = "H"),
            )
        )
        assertEquals(expected, actual)

        actual = viewModel.chooseAnswer("G")
        expected = UiState.Last(
            question = "question2",
            choices = listOf<ButtonChoiceUiState>(
                ButtonChoiceUiState.NotChosen(text = "E"),
                ButtonChoiceUiState.NotChosen(text = "F"),
                ButtonChoiceUiState.Correct(text = "G"),
                ButtonChoiceUiState.NotChosen(text = "H"),
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
}