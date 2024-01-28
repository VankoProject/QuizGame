package com.kliachenko.quizgame.progress

import androidx.lifecycle.ViewModel
import com.kliachenko.quizgame.game.GameScreen
import com.kliachenko.quizgame.main.NavigationObservable

class LoadViewModel(
    private val navigation: NavigationObservable,
    private val observable: UiObservable,
    private val repository: QuizRepository,
) : ViewModel() {

    private val callback = object : LoadCallback {
        override fun loadedSuccessfully() {
            navigation.navigate(GameScreen)
        }

        override fun loadError(error: String) {
            observable.updateUi(LoadUiState.Error(error))
        }
    }

    fun load() {
        observable.updateUi(LoadUiState.Progress)
        repository.loadData(callback)
    }

    fun startGettingUpdates(uiCallback: UiCallback) {
        observable.updateObserver(uiCallback)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UiCallback.Empty)
    }
}