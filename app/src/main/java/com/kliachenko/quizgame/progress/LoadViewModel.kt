package com.kliachenko.quizgame.progress

import com.kliachenko.quizgame.core.BaseViewModel
import com.kliachenko.quizgame.core.RunAsync
import com.kliachenko.quizgame.main.NavigationObservable

class LoadViewModel(
    private val navigation: NavigationObservable,
    private val observable: UiObservable,
    private val repository: QuizRepository,
    runAsync: RunAsync,
) : BaseViewModel(runAsync) {
    fun load() {
        observable.updateUi(LoadUiState.Progress)
        runAsync({ repository.loadData() }) { loadResult ->
            loadResult.handle(navigation, observable)
        }
    }

    fun startGettingUpdates(uiCallback: UiCallback) {
        observable.updateObserver(uiCallback)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UiCallback.Empty)
    }
}