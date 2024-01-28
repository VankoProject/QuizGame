package com.kliachenko.quizgame.main

import androidx.lifecycle.ViewModel
import com.kliachenko.quizgame.game.GameScreen
import com.kliachenko.quizgame.progress.LoadScreen

class MainViewModel(
    private val navigation: NavigationObservable,
    private val screenRepository: ScreenRepository.Read,
) : ViewModel() {

    fun init() {
        val screen = if (screenRepository.shouldLoadNewGame())
            LoadScreen
        else
            GameScreen
        navigation.navigate(screen)
    }

    fun startGettingUpdates(observer: Navigate) {
        navigation.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        navigation.updateObserver(Navigate.Empty)
    }

    fun notifyObserved() {

    }
}