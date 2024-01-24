package com.kliachenko.quizgame.main

import androidx.lifecycle.ViewModel
import com.kliachenko.quizgame.game.GameScreen
import com.kliachenko.quizgame.progress.LoadScreen

class MainViewModel(
    private val screenRepository: ScreenRepository.Read
) : ViewModel() {

    fun screen(): Screen = if (screenRepository.shouldLoadNewGame())
        LoadScreen
    else
        GameScreen
}