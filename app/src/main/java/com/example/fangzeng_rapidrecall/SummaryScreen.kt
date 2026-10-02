package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(modifier: Modifier = Modifier, onReturnToMenu: () -> Unit, numAttempts: Int, numCorrectAttempts: Int, accuracyPercentage: Float) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            IconButton(modifier = Modifier.size(64.dp).align(Alignment.CenterStart), onClick = {onReturnToMenu()}) {
                Icon(modifier = Modifier.size(42.dp), painter = painterResource(R.drawable.baseline_chevron_left_24), contentDescription = "Back")
            }
            Text("Summary", fontSize = 36.sp)
        }
        Text("$numAttempts attempts were made.", fontSize = 24.sp)
        Text("$numCorrectAttempts of those were correct.", fontSize = 24.sp)
        Text("Average accuracy is ${accuracyPercentage * 100}%.", fontSize = 24.sp)
        Text("Note: the accuracy of a game is the number of correct digits divided by the number of digits in that sequence." +
                " For example, if you guessed 123, but the sequence was 124, then the accuracy for that game is 2/3 = 66.6%", textAlign = TextAlign.Center)
    }
}