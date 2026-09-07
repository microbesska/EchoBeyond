package com.example.echobeyond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import com.example.echobeyond.screens.ChatScreen
import com.example.echobeyond.screens.ConnectionScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EchoBeyondScreen()
        }
    }
}

@Composable
fun EchoBeyondScreen() {

    val stage = remember {
        mutableIntStateOf(0)
    }

    val choice = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(Unit) {

        delay(1000)
        stage.intValue = 1

        delay(1200)
        stage.intValue = 2

        delay(1500)
        stage.intValue = 3

        delay(1500)
        stage.intValue = 4

        delay(1200)
        stage.intValue = 5
    }

    if (stage.intValue < 5) {

        ConnectionScreen(stage.intValue)

    } else {

        ChatScreen(
            choice = choice.intValue,
            onChoiceSelected = { selectedChoice ->
                choice.intValue = selectedChoice
            }
        )
    }
}