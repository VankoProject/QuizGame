package com.kliachenko.quizgame

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    init {
        Log.d("jsc91", "MainActivity init")
        Log.d("jsc91", "application is null ${application == null}")
    }

    private lateinit var viewModel: QuizViewModel
    private lateinit var uiState: UiState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("jsc91", "onCreate ${savedInstanceState == null}")
        setContentView(R.layout.activity_main)
        viewModel = (application as QuizApp).viewModel()

        val questionTextView: TextView = findViewById(R.id.questionTextView)
        val choiceOneButton = findViewById<Button>(R.id.choiceOneButton)
        val choiceTwoButton = findViewById<Button>(R.id.choiceTwoButton)
        val choiceThreeButton = findViewById<Button>(R.id.choiceThreeButton)
        val choiceFourButton = findViewById<Button>(R.id.choiceFourButton)
        val actionButton = findViewById<Button>(R.id.actionButton)

        choiceOneButton.setOnClickListener {
            uiState = viewModel.chooseAnswer(choiceOneButton.text.toString())
            uiState.show(questionTextView)
            uiState.show(
                choiceOneButton,
                choiceTwoButton,
                choiceThreeButton,
                choiceFourButton
            )
            uiState.show(actionButton, this)
        }
        choiceTwoButton.setOnClickListener {
            uiState = viewModel.chooseAnswer(choiceTwoButton.text.toString())
            uiState.show(questionTextView)
            uiState.show(
                choiceOneButton,
                choiceTwoButton,
                choiceThreeButton,
                choiceFourButton
            )
            uiState.show(actionButton, this)
        }
        choiceThreeButton.setOnClickListener {
            uiState = viewModel.chooseAnswer(choiceThreeButton.text.toString())
            uiState.show(questionTextView)
            uiState.show(
                choiceOneButton,
                choiceTwoButton,
                choiceThreeButton,
                choiceFourButton
            )
            uiState.show(actionButton, this)
        }
        choiceFourButton.setOnClickListener {
            uiState = viewModel.chooseAnswer(choiceFourButton.text.toString())
            uiState.show(questionTextView)
            uiState.show(
                choiceOneButton,
                choiceTwoButton,
                choiceThreeButton,
                choiceFourButton
            )
            uiState.show(actionButton, this)
        }
        actionButton.setOnClickListener {
            uiState = viewModel.next()
            uiState.show(questionTextView)
            uiState.show(
                choiceOneButton,
                choiceTwoButton,
                choiceThreeButton,
                choiceFourButton
            )
            uiState.show(actionButton, this)
        }

        uiState = if (savedInstanceState == null) {
            viewModel.init()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getSerializable("uiState", UiState::class.java) as UiState
            } else {
                savedInstanceState.getSerializable("uiState") as UiState
            }
        }
        uiState.show(questionTextView)
        uiState.show(choiceOneButton, choiceTwoButton, choiceThreeButton, choiceFourButton)
        uiState.show(actionButton, this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("uiState", uiState)
    }
}