package com.kliachenko.quizgame.progress

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

interface LoadUiState {

    fun update(progressBar: ProgressBar, errorTextView: TextView, retryButton: Button)

    object Progress : LoadUiState {
        override fun update(
            progressBar: ProgressBar,
            errorTextView: TextView,
            retryButton: Button,
        ) {
            progressBar.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
    }

    data class Error(private val message: String) : LoadUiState {
        override fun update(
            progressBar: ProgressBar,
            errorTextView: TextView,
            retryButton: Button,
        ) {
            progressBar.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = message
            retryButton.visibility = View.VISIBLE
        }
    }

    object Empty : LoadUiState {
        override fun update(
            progressBar: ProgressBar,
            errorTextView: TextView,
            retryButton: Button,
        ) = Unit
    }
}
