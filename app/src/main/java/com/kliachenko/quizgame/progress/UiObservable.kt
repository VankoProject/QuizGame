package com.kliachenko.quizgame.progress

interface UiObservable: UpdateObserver, UpdateUi {

    class Base :UiObservable {
        private var cache: LoadUiState = LoadUiState.Empty
        private var observer: UiCallback = UiCallback.Empty
        override fun updateObserver(observer: UiCallback) { //onViewCreated
            this.observer = observer //LoadUiState.Progress
            observer.update(cache) //Empty # Unit
        }

        override fun updateUi(loadUiState: LoadUiState) { //observer = fragment || Empty
            cache = loadUiState
            observer.update(cache) //fragment#update (Progress)
        }
    }
}

interface UpdateUi {
    fun updateUi(loadUiState: LoadUiState)
}

interface UpdateObserver {
    fun updateObserver(observer: UiCallback)
}