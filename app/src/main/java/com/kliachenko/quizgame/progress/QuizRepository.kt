package com.kliachenko.quizgame.progress

import com.kliachenko.quizgame.main.ScreenRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

interface QuizRepository {
    fun loadData(callback: LoadCallback)
    class Base(
        private val quizCacheDataSource: QuizCacheDataSource.Save,
        private val screenRepository: ScreenRepository.Save,
        private val service: QuizService,
    ) : QuizRepository {

        override fun loadData(callback: LoadCallback) {
            service.load().enqueue(object : Callback<QuizResponse> {
                override fun onResponse(
                    call: Call<QuizResponse>,
                    response: Response<QuizResponse>,
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.responseCode == 0 && it.results.isNotEmpty()) {
                                quizCacheDataSource.save(it)
                                screenRepository.saveGameAlreadyStarted()
                                callback.loadedSuccessfully()
                            } else {
                                callback.loadError("server error")
                            }
                        } ?: callback.loadError("empty result body")
                    } else {
                        callback.loadError(response.errorBody().toString())
                    }
                }
                override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                    if (t is UnknownHostException)
                        callback.loadError("no internet connection")
                    else
                        callback.loadError(t.message ?: "connection error")
                }
            })
        }
    }
}

interface LoadCallback {
    fun loadedSuccessfully()
    fun loadError(error: String)
}