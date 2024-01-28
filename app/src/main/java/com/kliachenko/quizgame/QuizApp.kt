package com.kliachenko.quizgame

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.kliachenko.quizgame.game.GameRepository
import com.kliachenko.quizgame.game.PermanentStorage
import com.kliachenko.quizgame.game.QuizViewModel
import com.kliachenko.quizgame.main.LocalStorage
import com.kliachenko.quizgame.main.MainViewModel
import com.kliachenko.quizgame.main.ScreenRepository
import com.kliachenko.quizgame.progress.LoadViewModel
import com.kliachenko.quizgame.progress.QuizCacheDataSource
import com.kliachenko.quizgame.progress.QuizRepository
import com.kliachenko.quizgame.progress.QuizService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        context: Context,
    ) : ViewModelProviderFactory {

        private val viewModelStore = HashMap<Class<*>, ViewModel>()
        private val localStorage = LocalStorage.Base(context)
        private val quizCacheDataSource: QuizCacheDataSource.Mutable = QuizCacheDataSource.Base(
            Gson(),
            localStorage
        )
        private val screenRepository = ScreenRepository.Base(localStorage)

        override fun <T : ViewModel> viewModel(clasz: Class<out T>): T {
            return if (viewModelStore.containsKey(clasz))
                viewModelStore[clasz] as T
            else {
                val vm = when (clasz) {
                    MainViewModel::class.java -> MainViewModel(ScreenRepository.Base(localStorage))
                    LoadViewModel::class.java -> {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        val client: OkHttpClient = OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .build()
                        val retrofit = Retrofit.Builder()
                            .baseUrl("https://opentdb.com/")
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                        LoadViewModel(
                            QuizRepository.Base(
                                quizCacheDataSource,
                                screenRepository,
                                retrofit.create(QuizService::class.java)
                            )
                        )
                    }

                    QuizViewModel::class.java -> QuizViewModel(
                        GameRepository.Base(
                            quizCacheDataSource,
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