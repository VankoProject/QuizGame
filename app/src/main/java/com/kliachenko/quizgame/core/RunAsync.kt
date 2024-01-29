package com.kliachenko.quizgame.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RunAsync {
    fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit,
    ): Job

    class Base(
        private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
        private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    ) : RunAsync {
        override fun <T : Any> start(
            coroutineScope: CoroutineScope,
            background: suspend () -> T,
            uiBlock: (T) -> Unit,
        ) = coroutineScope.launch(dispatcherIO) {
            val result = background.invoke()
            withContext(dispatcherMain) {
                uiBlock.invoke(result)
            }
        }
    }
}