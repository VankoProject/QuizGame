package com.kliachenko.quizgame

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.kliachenko.quizgame.game.GameRepository
import com.kliachenko.quizgame.game.PermanentStorage
import com.kliachenko.quizgame.game.QuizViewModel
import com.kliachenko.quizgame.main.LocalStorage
import com.kliachenko.quizgame.main.MainViewModel
import com.kliachenko.quizgame.main.ScreenRepository

class QuizApp : Application(), ViewModelProviderFactory {

    private lateinit var factory: ViewModelProviderFactory

    override fun onCreate() {
        super.onCreate()
        factory = ViewModelProviderFactory.Base(BuildConfig.DEBUG, this)
    }

    override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
        return factory.viewModel(clasz)
    }
}

interface ViewModelProviderFactory {

    fun <T : ViewModel> viewModel(clasz: Class<out T>): T

    class Base(
        private val isRelease: Boolean,
        context: Context
    ) : ViewModelProviderFactory {

        private val viewModelStore = HashMap<Class<*>, ViewModel>()

        private val localStorage = LocalStorage.Base(context)

        override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
            return if (viewModelStore.containsKey(clasz))
                viewModelStore[clasz] as T
            else {
                val vm = when (clasz) {
                    MainViewModel::class.java -> MainViewModel(ScreenRepository.Base(localStorage))
                    QuizViewModel::class.java -> QuizViewModel(
                        GameRepository.Base(
                            if (isRelease)
                                PermanentStorage.Base(localStorage)
                            else
                                PermanentStorage.Base(localStorage)
                        )
                    )

                    else -> throw IllegalStateException("unknown")
                } as T
                viewModelStore[clasz] = vm
                vm
            }
        }
    }
}