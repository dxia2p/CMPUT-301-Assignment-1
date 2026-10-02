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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fangzeng_rapidrecall.ui.theme.FangzengRapidRecallTheme


// Enum class for navHost
enum class GameScreen() {
    MainMenu,
    Game,
    Log,
    Summary,
}


class MainActivity : ComponentActivity() {

    val gameViewModel: GameViewModel = GameViewModel()
    val gameStorage: GameStorage = GameStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FangzengRapidRecallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // I learned how to navigate between screens through this link: https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#0
                    var navController: NavHostController = rememberNavController()

                    NavHost(navController = navController, startDestination = GameScreen.MainMenu.name, Modifier.padding(innerPadding)) {
                        composable(route = GameScreen.MainMenu.name) {
                            MainMenu(
                                modifier = Modifier.fillMaxSize().padding(innerPadding),
                                {
                                    navController.navigate(GameScreen.Game.name)
                                    gameViewModel.startGame(it)
                                },
                                {
                                    navController.navigate(GameScreen.Log.name)
                                },
                                {
                                    navController.navigate(GameScreen.Summary.name)
                                }
                            )
                        }
                        composable(route = GameScreen.Game.name) {
                            GameScreen(
                                modifier = Modifier.fillMaxSize().padding(
                                    top = innerPadding.calculateTopPadding(),
                                    bottom = innerPadding.calculateBottomPadding(),
                                    start = 16.dp,
                                    end = 16.dp  // For some reason if I don't override the side values, there's no padding on the sides??
                                    ),
                                gameViewModel,
                                onReturnToMenuClicked = {
                                    navController.navigate(GameScreen.MainMenu.name)

                                },
                                onGameEnd = {
                                        sequenceLen: Int, userInput: List<Int>, targetSeq: List<Int>, timestamp: String ->
                                    gameStorage.addGame(sequenceLen, userInput, targetSeq, timestamp)
                                }

                            )
                        }
                        composable(route = GameScreen.Log.name) {
                            LogScreen(modifier = Modifier.fillMaxSize().padding(
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding(),
                                start = 16.dp,
                                end = 16.dp  // For some reason if I don't override the side values, there's no padding on the sides??
                            ),
                                gameStorage.getGamesList(), {navController.navigate(GameScreen.MainMenu.name)})
                        }
                        composable(route = GameScreen.Summary.name) {
                            SummaryScreen(modifier = Modifier.fillMaxSize().padding(
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding(),
                                start = 16.dp,
                                end = 16.dp  // For some reason if I don't override the side values, there's no padding on the sides??
                                ),
                                {navController.navigate(GameScreen.MainMenu.name)},
                                gameStorage.getNumAttempts(),
                                gameStorage.getNumCorrectAttempts(),
                                gameStorage.getAccuracyPercentage()
                            )
                        }
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