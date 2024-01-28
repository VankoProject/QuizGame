package com.kliachenko.quizgame.main

interface NavigationObservable : Navigate, UpdateNavigateObserver {

    fun clear()
    class Base : NavigationObservable {
        private var cachedScreen: Screen = Screen.Empty
        private var observer: Navigate = Navigate.Empty

        override fun navigate(screen: Screen) {
            cachedScreen = screen
            observer.navigate(screen)
        }
        override fun updateObserver(observer: Navigate) {
            this.observer = observer
            observer.navigate(cachedScreen)
        }
        override fun clear() {
            cachedScreen = Screen.Empty
        }
    }
}

interface Navigate {
    fun navigate(screen: Screen)

    object Empty : Navigate {
        override fun navigate(screen: Screen) = Unit
    }
}

interface UpdateNavigateObserver {
    fun updateObserver(observer: Navigate)
}