package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenu(modifier: Modifier = Modifier, startGameClicked: (Int) -> Unit, logClicked: () -> Unit, summaryClicked: () -> Unit) {
    val MIN_SEQUENCE_LEN = 1
    val MAX_SEQUENCE_LEN = 10
    var sequenceLen by remember { mutableIntStateOf(1) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically)) {
        Text(text = "Recall", fontSize = 48.sp, fontFamily = FontFamily.Monospace)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {startGameClicked(sequenceLen)}) {
                Text("Start")
            }
            Button(onClick = {logClicked()}) {
                Text("Log")
            }
            Button(onClick={summaryClicked()}) {
                Text("Summary")
            }
        }

        // A box that contains a number from 1-10 and allows the user to increment/decrement it with arrow buttons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Sequence Length: ", fontSize = 24.sp)
            IconButton(onClick = {
                sequenceLen--
                if (sequenceLen < MIN_SEQUENCE_LEN) sequenceLen = MIN_SEQUENCE_LEN
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_chevron_left_24),
                    contentDescription = "Decrease sequence length"
                )
            }

            Box(modifier = Modifier
                .width(48.dp)
                .padding(8.dp), contentAlignment = Alignment.Center) {
                Text(text = sequenceLen.toString(), fontSize = 24.sp)
            }

            IconButton(onClick={
                sequenceLen++
                if (sequenceLen > MAX_SEQUENCE_LEN) sequenceLen = MAX_SEQUENCE_LEN
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_chevron_right_24),
                    contentDescription = "Increase sequence length"
                )
            }
        }
    }

}