package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.time.Instant

fun convertStringToNumList(str: String): MutableList<Int> {
    val seq: MutableList<Int> = mutableListOf()
    for (i in 0 until str.length) {
        seq.add(str[i].digitToInt())
    }
    return seq
}


@Composable
fun GameScreen(modifier: Modifier = Modifier, viewModel: GameViewModel, onReturnToMenuClicked: () -> Unit,
               onGameEnd: (sequenceLen: Int, userInput: List<Int>, targetSeq: List<Int>, timestamp: String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var playerSequence by remember{mutableStateOf("")}

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(36.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Remember the numbers shown below!", fontSize = 20.sp)

        if (uiState.displayDigit) {
            Text(uiState.currentDigit.toString(), fontSize = 48.sp)
        } else {
            Text("-", fontSize = 48.sp)
        }

        if (uiState.feedbackStr != "") {
            Text(text = uiState.feedbackStr, textAlign = TextAlign.Center, fontSize = 20.sp)
        }

        if (uiState.showInputBox) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    value = playerSequence,
                    onValueChange = {newValue ->
                        if (newValue.all { it.isDigit() }) {
                            playerSequence = newValue
                        }
                    },
                    label = {Text("Enter sequence here...")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                IconButton(onClick = {
                    if (playerSequence.length == 0) return@IconButton
                    val seq: MutableList<Int> = convertStringToNumList(playerSequence)
                    viewModel.submitSequence(seq)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_forward_24),
                        contentDescription = "Submit sequence"
                    )
                }
            }
        }

        if (uiState.showBackButton) {
            Button(onClick = {

                onGameEnd(viewModel.seqLen, convertStringToNumList(playerSequence), viewModel.sequence,
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()))
                onReturnToMenuClicked()
            }) {
                Text("Return to menu")
            }
        }
    }
}