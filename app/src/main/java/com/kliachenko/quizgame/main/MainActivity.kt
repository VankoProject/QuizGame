package com.kliachenko.quizgame.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.quizgame.R
import com.kliachenko.quizgame.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel =
            (application as ViewModelProviderFactory).viewModel(MainViewModel::class.java)

        if (savedInstanceState == null) {
            val screen = viewModel.screen()
            screen.show(R.id.container, supportFragmentManager)
        }
    }
}