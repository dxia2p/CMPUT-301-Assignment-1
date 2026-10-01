package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
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

data class SequenceUiState(
    val currentDigit: Int = -1,
    val displayDigit: Boolean = false,
    val gameStarted: Boolean = false,
)

// This class contains information about the game state, such as the digit that is currently displayed, and whether the game started
// The class inherits from ViewModel because I need coroutines to implement a delay between changing the digits in the game
class GameViewModel : ViewModel() {
    private val sequence = mutableListOf<Int>()
    private val _uiState = MutableStateFlow(SequenceUiState())
    val uiState: StateFlow<SequenceUiState> = _uiState.asStateFlow()
    public fun startGame(sequenceLen: Int) {
        _uiState.update { currentState->
            currentState.copy(gameStarted = true)
        }

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
        }
    }
}

@Composable
fun gameScreen(viewModel: GameViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        if (uiState.displayDigit) {
            Text(uiState.currentDigit.toString(), fontSize = 48.sp)
        } else {
            Text("", fontSize = 48.sp)
        }
    }
}