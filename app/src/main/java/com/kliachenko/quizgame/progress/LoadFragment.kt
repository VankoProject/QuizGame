package com.kliachenko.quizgame.progress

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kliachenko.quizgame.R
import com.kliachenko.quizgame.ViewModelProviderFactory

class LoadFragment : Fragment(R.layout.fragment_load) {

    private lateinit var viewModel: LoadViewModel
    private lateinit var uiCallback: UiCallback
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel =
            (requireActivity().application as ViewModelProviderFactory).viewModel(LoadViewModel::class.java)

        val retryButton = view.findViewById<Button>(R.id.retryButton)
        val errorTextView = view.findViewById<TextView>(R.id.errorTextView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        uiCallback = object : UiCallback {
            override fun update(loadUiState: LoadUiState) =
                loadUiState.update(progressBar, errorTextView, retryButton)
        }

        retryButton.setOnClickListener {
            viewModel.load()
        }

        if (savedInstanceState == null) {
            viewModel.load()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(uiCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}

interface UiCallback {
    fun update(loadUiState: LoadUiState)
    object Empty : UiCallback {
        override fun update(loadUiState: LoadUiState) = Unit
    }
}