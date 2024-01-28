package com.kliachenko.quizgame.game

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.kliachenko.quizgame.R
import java.io.Serializable

interface UiState : Serializable {

    fun show(questionTextView: TextView)
    fun show(vararg choices: ChoiceButton)
    fun show(actionButton: ActionButton, activity: FragmentActivity)

    data class Question(
        private val question: String,
        private val choices: List<ChoiceUiState>
    ) : UiState {
        override fun show(questionTextView: TextView) {
            questionTextView.text = question
        }

        override fun show(vararg choices: ChoiceButton) {
            this.choices.forEachIndexed { index, choiceUiState ->
                choices[index].updateState(choiceUiState)
            }
        }

        override fun show(actionButton: ActionButton, activity: FragmentActivity) {
            actionButton.updateState(View.GONE)
        }
    }

    data class Answered(
        private val choices: List<ChoiceUiState>
    ) : UiState {
        override fun show(questionTextView: TextView) {
        }

        override fun show(vararg choices: ChoiceButton) {
            this.choices.forEachIndexed { index, choiceUiState ->
                choices[index].updateState(choiceUiState)
            }
        }

        override fun show(actionButton: ActionButton, activity: FragmentActivity) = with(actionButton) {
            visibility = View.VISIBLE
            setText(R.string.next)
        }
    }

    data class Last(
        private val choices: List<ChoiceUiState>
    ) : UiState {
        override fun show(questionTextView: TextView) {
        }

        override fun show(vararg choices: ChoiceButton) {
            this.choices.forEachIndexed { index, choiceUiState ->
                choices[index].updateState(choiceUiState)
            }
        }

        override fun show(actionButton: ActionButton, activity: FragmentActivity) = with(actionButton) {
            visibility = View.VISIBLE
            setText(R.string.game_over)
        }
    }

    object GameOver : UiState {
        override fun show(questionTextView: TextView) = Unit
        override fun show(vararg choices: ChoiceButton) = Unit
        override fun show(actionButton: ActionButton, activity: FragmentActivity) = activity.finish()
    }
}

interface ChoiceUiState : Serializable {
    fun show(button: ChoiceButton)

    object Empty : ChoiceUiState {
        override fun show(button: ChoiceButton) = Unit
    }

    abstract class Abstract(
        private val color: String,
        private val clickable: Boolean = false
    ) :
        ChoiceUiState {
        override fun show(button: ChoiceButton) {
            button.setBackgroundColor(Color.parseColor(color))
            button.isClickable = clickable
        }
    }

    data class Question(private val value: String) : Abstract("#7A84DA", true) {
        override fun show(button: ChoiceButton) = with(button) {
            super.show(button)
            button.text = value
        }
    }

    object Correct : Abstract("#80E38A")

    object Incorrect : Abstract("#E63B3B")

    object NotChosen : Abstract("#6E7292")
}

