package com.example.fangzeng_rapidrecall

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Summary", fontSize = 48.sp)
    }
}