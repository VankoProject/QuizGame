package com.kliachenko.quizgame.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kliachenko.quizgame.R
import com.kliachenko.quizgame.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var navigate: Navigate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel =
            (application as ViewModelProviderFactory).viewModel(MainViewModel::class.java)

        navigate = object : Navigate {
            override fun navigate(screen: Screen) {
                screen.show(R.id.container, supportFragmentManager)
                viewModel.notifyObserved()
            }
        }

        if (savedInstanceState == null) {
            viewModel.init()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(navigate)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}