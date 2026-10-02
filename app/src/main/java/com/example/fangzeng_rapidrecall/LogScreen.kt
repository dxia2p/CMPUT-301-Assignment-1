package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LogScreen(modifier: Modifier = Modifier, gameDataList: List<SingleGameData>, onReturnToMenu: () -> Unit) {
    Column(modifier = modifier) {
        Button(onClick = {onReturnToMenu()}) {
            Text("Return to menu")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(gameDataList) { index, gameData ->
                GameDataRow(index = index + 1, gameData = gameData)
            }
        }
    }
}

@Composable
fun GameDataRow(modifier: Modifier = Modifier, index: Int, gameData: SingleGameData) {
    Text("${index.toString()}. Sequence Length: ${gameData.sequenceLen}\n" +
            "User input: ${gameData.userSequence}\n" +
            "Target sequence: ${gameData.targetSequence}\n" +
            "Correct: ${gameData.attemptCorrect}\n" +
            "Timestamp: ${gameData.timestamp}")
}