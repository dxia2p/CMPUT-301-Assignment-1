package com.example.fangzeng_rapidrecall

import java.sql.Timestamp
import kotlin.math.min

// This class holds the data for a single completed game
data class SingleGameData (val sequenceLen: Int, val userSequence: List<Int>,
                           val targetSequence: List<Int>, val attemptCorrect: Boolean, val timestamp: String)

// This class stores all played games this session, and has helper functions to get and aggregate game data
class GameStorage() {
    private val gameDataList: MutableList<SingleGameData> = mutableListOf()

    fun addGame(sequenceLen: Int, userSequence: List<Int>, targetSequence: List<Int>, timestamp: String) {
        var attemptCorrect: Boolean = userSequence.size == targetSequence.size
        for(i in 0 until min(userSequence.size, targetSequence.size)) {
            if (userSequence[i] != targetSequence[i]) {
                attemptCorrect = false
            }
        }
        gameDataList.add(SingleGameData(sequenceLen, userSequence, targetSequence, attemptCorrect, timestamp))
    }

    fun getGamesList(): List<SingleGameData> {
        return gameDataList
    }

    fun getNumAttempts(): Int {
        return gameDataList.size
    }

    fun getNumCorrectAttempts(): Int {
        var result: Int = 0
        for (i in 0 until gameDataList.size) {
            if (gameDataList[i].attemptCorrect) {
                result++
            }
        }
        return result
    }

    fun getAccuracyPercentage(): Float {
        var result: Float = 0.0f
        for (gameData in gameDataList) {
            var correctDigits: Int = 0
            for (i in 0 until min(gameData.targetSequence.size, gameData.userSequence.size)) {
                if (gameData.userSequence[i] == gameData.targetSequence[i]) {
                    correctDigits++
                }
            }
            var accuracy: Float = correctDigits.toFloat() / gameData.targetSequence.size.toFloat()
            result += accuracy
        }
        result / gameDataList.size
        return result
    }
}