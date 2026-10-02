package com.example.fangzeng_rapidrecall


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

val DIGIT_DISPLAY_TIME = 1500L
val TIME_BTW_DIGITS = 500L

// This class holds data for the GameViewModel
data class SequenceUiState(
    val currentDigit: Int = -1,
    val displayDigit: Boolean = false,
    val gameStarted: Boolean = false,
    val showInputBox: Boolean = false,
    val feedbackStr: String = "",
    val showBackButton: Boolean = false,
)

// This class contains information about the game state, such as the digit that is currently displayed, and whether the game started
// The class inherits from ViewModel because I need coroutines to implement a delay between changing the digits in the game
// GameViewModel is based off the first code example in https://developer.android.com/topic/libraries/architecture/viewmodel
class GameViewModel : ViewModel() {
    val sequence = mutableListOf<Int>()
    var seqLen: Int = 0
    private val _uiState = MutableStateFlow(SequenceUiState())
    val uiState: StateFlow<SequenceUiState> = _uiState.asStateFlow()
    public fun startGame(sequenceLen: Int) {
        sequence.clear()
        _uiState.value = SequenceUiState(gameStarted = true)
        seqLen = sequenceLen

        for(i in 1..sequenceLen) {
            sequence.add(Random.nextInt(0, 10))
        }

        viewModelScope.launch {
            delay(1000L)

            for(digit in sequence) {
                _uiState.update { currentState ->
                    currentState.copy(
                        currentDigit = digit,
                        displayDigit = true
                    )
                }

                delay(DIGIT_DISPLAY_TIME)

                _uiState.update { currentState ->
                    currentState.copy(
                        displayDigit = false
                    )
                }

                delay(TIME_BTW_DIGITS)
            }

            _uiState.update { currentState ->
                currentState.copy(
                    showInputBox = true
                )
            }
        }

    }

    public fun submitSequence(playerSequence: MutableList<Int>) {
        var answerCorrect: Boolean = true
        if (playerSequence.size != sequence.size) {
            // Incorrect answer
            answerCorrect = false
        } else {
            for (i in 0 until sequence.size) {
                if (sequence[i] != playerSequence[i]) {
                    answerCorrect = false
                }
            }
        }

        if (answerCorrect) {
            var str: String = "You win! the sequence was "
            for(i in 0 until sequence.size) {
                str += sequence[i].toString()
            }
            str += "."
            _uiState.update {
                currentState ->
                currentState.copy(
                    feedbackStr = str,
                )
            }
        } else {
            var str: String = "Incorrect. the sequence was "
            for(i in 0 until sequence.size) {
                str += sequence[i].toString()
            }
            str += ", but you entered "
            for(i in 0 until playerSequence.size) {
                str += playerSequence[i].toString()
            }
            str += "."

            _uiState.update {
                currentState ->
                currentState.copy(
                    feedbackStr = str,
                )
            }
        }

        _uiState.update {
            currentState ->
            currentState.copy(
                showInputBox = false,
                showBackButton = true
            )
        }
    }
}

