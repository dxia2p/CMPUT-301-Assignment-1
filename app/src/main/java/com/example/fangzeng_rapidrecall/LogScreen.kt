package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogScreen(modifier: Modifier = Modifier, gameDataList: List<SingleGameData>, onReturnToMenu: () -> Unit) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            IconButton(modifier = Modifier.size(64.dp).align(Alignment.CenterStart), onClick = {onReturnToMenu()}) {
                Icon(modifier = Modifier.size(42.dp), painter = painterResource(R.drawable.baseline_chevron_left_24), contentDescription = "Back")
            }
            Text("Log", fontSize = 36.sp)
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
    HorizontalDivider(thickness = 1.dp, color = Color.Gray)

}