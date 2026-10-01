package com.example.fangzeng_rapidrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fangzeng_rapidrecall.ui.theme.FangzengRapidRecallTheme

val MIN_SEQUENCE_LEN = 1
val MAX_SEQUENCE_LEN = 10
// TODO: Implement screen changes in a better way (right now we check whether the game started in the main function and display the corresponding screen, but this gets clunky with more than 2 screens)
@Composable
fun MainMenu(modifier: Modifier = Modifier, startGameClicked: (Int) -> Unit, summaryClicked: () -> Unit) {
    var sequenceLen by remember { mutableIntStateOf(1) }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {startGameClicked(sequenceLen)}) {
                Text("Start")
            }
            Button(onClick={}) {
                Text("Summary")
            }
        }

        // A box that contains a number from 1-10 and allows the user to increment/decrement it with arrow buttons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Sequence Length: ")
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
                Text(text = sequenceLen.toString())
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



class MainActivity : ComponentActivity() {

    fun startGameClicked(sequenceLen: Int) {
        gameViewModel.startGame(sequenceLen)
    }

    fun summaryClicked() {

    }

    val gameViewModel: GameViewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FangzengRapidRecallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val gameUiState by gameViewModel.uiState.collectAsState()
                    if (gameUiState.gameStarted) {
                         gameScreen(gameViewModel)
                    } else {
                        MainMenu(modifier = Modifier.padding(innerPadding), {startGameClicked(it)},
                            { summaryClicked() })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FangzengRapidRecallTheme {
        Greeting("Android")
    }
}