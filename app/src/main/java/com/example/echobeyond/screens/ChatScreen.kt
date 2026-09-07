package com.example.echobeyond.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echobeyond.story.firstSceneSilent
import com.example.echobeyond.story.firstSceneStart
import com.example.echobeyond.story.firstSceneYes
import kotlinx.coroutines.delay


@Composable
fun ChatScreen(
    choice: Int,
    onChoiceSelected: (Int) -> Unit
) {

    // =========================================================
    // СЦЕНАРИЙ
    // =========================================================

    val messages = when (choice) {

        0 -> firstSceneStart

        1 -> firstSceneYes

        else -> firstSceneSilent
    }


    // =========================================================
    // СОСТОЯНИЕ ЧАТА
    // =========================================================

    var visibleMessages by remember(choice) {
        mutableIntStateOf(0)
    }

    var isTyping by remember(choice) {
        mutableStateOf(false)
    }

    var typingDots by remember(choice) {
        mutableIntStateOf(1)
    }


    // =========================================================
    // ПРОКРУТКА
    // =========================================================

    val scrollState = rememberScrollState()


    // =========================================================
    // ПОСЛЕДОВАТЕЛЬНОЕ ПОЯВЛЕНИЕ СООБЩЕНИЙ
    // =========================================================

    LaunchedEffect(choice) {

        visibleMessages = 0
        isTyping = true

        for (i in messages.indices) {

            // Анимация трёх точек перед сообщением

            var elapsed = 0

            while (elapsed < 900) {

                typingDots = (typingDots % 3) + 1

                delay(300)

                elapsed += 300
            }


            // Показываем новое сообщение

            visibleMessages = i + 1
            isTyping = false


            // Небольшая пауза перед следующим сообщением

            if (i < messages.lastIndex) {

                delay(600)

                isTyping = true
            }
        }
    }


    // =========================================================
    // АВТОПРОКРУТКА
    // =========================================================

    LaunchedEffect(
        visibleMessages,
        isTyping
    ) {

        delay(50)

        scrollState.animateScrollTo(
            scrollState.maxValue
        )
    }


    // =========================================================
    // ЭКРАН ЧАТА
    // =========================================================

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101014))
            .verticalScroll(scrollState)
            .padding(16.dp),

        verticalArrangement = Arrangement.Top
    ) {


        // =====================================================
        // ЗАГОЛОВОК
        // =====================================================

        Text(

            text = "ЭХО ПО ТУ СТОРОНУ",

            color = Color(0xFFD4A84F),

            fontSize = 20.sp,

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )


        // =====================================================
        // СООБЩЕНИЯ
        // =====================================================

        for (
        i in 0 until visibleMessages
            .coerceAtMost(messages.size)
        ) {

            Message(

                character = messages[i].character,

                text = messages[i].text
            )
        }


        // =====================================================
        // ТРИ ТОЧКИ — СОБЕСЕДНИК ПЕЧАТАЕТ
        // =====================================================

        if (
            isTyping &&
            visibleMessages < messages.size
        ) {

            Text(

                text = ".".repeat(typingDots),

                color = Color(0xFFB8A7D9),

                fontSize = 22.sp,

                modifier = Modifier
                    .padding(bottom = 18.dp)
            )
        }


        // =====================================================
        // ВАРИАНТЫ ОТВЕТА
        // =====================================================

        if (
            choice == 0 &&
            visibleMessages >= messages.size
        ) {

            Spacer(
                modifier = Modifier.height(24.dp)
            )


            Text(

                text = "Что вы ответите?",

                color = Color.White,

                fontSize = 18.sp,

                modifier = Modifier
                    .padding(bottom = 12.dp)
            )


            // ---------------------------------------------
            // ВАРИАНТ 1
            // ---------------------------------------------

            ChoiceButton(

                text = "Да. Кто вы?",

                onClick = {

                    onChoiceSelected(1)
                }
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // ---------------------------------------------
            // ВАРИАНТ 2
            // ---------------------------------------------

            ChoiceButton(

                text = "Не отвечать",

                onClick = {

                    onChoiceSelected(2)
                }
            )
        }
    }
}


// =============================================================
// СООБЩЕНИЕ
// =============================================================

@Composable
fun Message(
    character: String,
    text: String
) {

    // Проверяем, принадлежит ли сообщение игроку

    val isPlayer = character == "Вы"


    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp),

        horizontalAlignment =
        if (isPlayer) {

            Alignment.End

        } else {

            Alignment.Start
        }
    ) {


        // =====================================================
        // ИМЯ
        // =====================================================

        Text(

            text = character,

            color =
            if (isPlayer) {

                Color(0xFFD4A84F)

            } else {

                Color(0xFFB8A7D9)
            },

            fontSize = 13.sp
        )


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        // =====================================================
        // ПУЗЫРЁК
        // =====================================================

        Box(

            modifier = Modifier
                .widthIn(max = 320.dp)
                .background(

                    color =
                    if (isPlayer) {

                        Color(0xFF3A3022)

                    } else {

                        Color(0xFF292332)
                    },

                    shape = RoundedCornerShape(16.dp)
                )
                .padding(

                    horizontal = 14.dp,

                    vertical = 11.dp
                )
        ) {

            Text(

                text = text,

                color = Color(0xFFE8E8E8),

                fontSize = 16.sp,

                lineHeight = 23.sp
            )
        }
    }
}


// =============================================================
// КНОПКА ВЫБОРА
// =============================================================

@Composable
fun ChoiceButton(
    text: String,
    onClick: () -> Unit
) {

    Button(

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth(),

        colors = ButtonDefaults.buttonColors(

            containerColor =
            Color(0xFF292332),

            contentColor =
            Color.White
        )
    ) {

        Text(

            text = text,

            fontSize = 16.sp
        )
    }
}