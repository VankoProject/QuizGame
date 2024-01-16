package com.kliachenko.quizgame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: QuizViewModel = QuizViewModel()

        val questionText = findViewById<TextView>(R.id.questionTextView)
        val choiceOneButton = findViewById<Button>(R.id.choiceOneButton)
        val choiceSecondButton = findViewById<Button>(R.id.choiceSecondButton)
        val choiceThreeButton = findViewById<Button>(R.id.choiceThreeButton)
        val choiceFourButton = findViewById<Button>(R.id.choiceFourButton)
        val actionButton = findViewById<Button>(R.id.actionButton)

        choiceOneButton.setOnClickListener {
            val uiState: UiState = viewModel.choose(choiceOneButton.text.toString())
        }

        choiceSecondButton.setOnClickListener {
            val uiState: UiState = viewModel.choose(choiceOneButton.text.toString())
        }

        choiceThreeButton.setOnClickListener {
            val uiState: UiState = viewModel.choose(choiceOneButton.text.toString())
        }

        choiceFourButton.setOnClickListener {
            val uiState: UiState = viewModel.choose(choiceOneButton.text.toString())
        }

        actionButton.setOnClickListener {
            val uiState: UiState = viewModel.next()
        }

        val uiState: UiState = viewModel.init()

    }
}