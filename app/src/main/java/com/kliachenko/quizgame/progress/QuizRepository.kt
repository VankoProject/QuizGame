package com.kliachenko.quizgame.progress

import com.kliachenko.quizgame.game.GameScreen
import com.kliachenko.quizgame.main.NavigationObservable
import com.kliachenko.quizgame.main.ScreenRepository
import java.net.UnknownHostException

interface QuizRepository {
    suspend fun loadData(): LoadResult
    class Base(
        private val quizCacheDataSource: QuizCacheDataSource.Save,
        private val screenRepository: ScreenRepository.Save,
        private val service: QuizService,
    ) : QuizRepository {

        override suspend fun loadData(): LoadResult = try {
            val response = service.load().execute()
            val body = response.body()!!
            if (body.responseCode == 0 && body.results.isNotEmpty()) {
                quizCacheDataSource.save(body)
                screenRepository.saveGameAlreadyStarted()
                LoadResult.Success
            } else {
                throw IllegalStateException("server exception")
            }

        } catch (t: Exception) {
            if (t is UnknownHostException)
                LoadResult.Error("no internet connection")
            else
                LoadResult.Error("service unavailable")
        }
    }
}



interface LoadResult {
    fun handle(navigation: NavigationObservable, observable: UiObservable)

    data class Error(private val message: String) : LoadResult {
        override fun handle(navigation: NavigationObservable, observable: UiObservable) {
            observable.updateUi(LoadUiState.Error(message))
        }
    }

    object Success : LoadResult {
        override fun handle(navigation: NavigationObservable, observable: UiObservable) {
            navigation.navigate(GameScreen)
        }

    }
}