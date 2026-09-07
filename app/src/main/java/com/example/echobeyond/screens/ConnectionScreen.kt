package com.example.echobeyond.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun ConnectionScreen(stage: Int) {

    // Анимация движения света по периметру
    val infiniteTransition = rememberInfiniteTransition(
        label = "perimeter_animation"
    )

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "perimeter_progress"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // =========================================================
        // СВЕТЯЩАЯСЯ РАМКА
        // =========================================================

        if (stage >= 1) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                // Отступ рамки от краёв экрана
                val inset = 18.dp.toPx()

                val left = inset
                val top = inset
                val right = size.width - inset
                val bottom = size.height - inset

                // Радиус закругления углов
                val cornerRadius = 45.dp.toPx()


                // Размеры прямых участков

                val straightWidth =
                    right - left - cornerRadius * 2

                val straightHeight =
                    bottom - top - cornerRadius * 2


                // Длина одного закруглённого угла

                val cornerLength =
                    PI.toFloat() * cornerRadius / 2f


                // Общая длина периметра

                val perimeter =
                    straightWidth * 2f +
                            straightHeight * 2f +
                            cornerLength * 4f


                // =================================================
                // Точка на периметре
                // =================================================

                fun pointOnPerimeter(
                    normalizedPosition: Float
                ): Offset {

                    var distance =
                        ((normalizedPosition % 1f) + 1f) % 1f *
                                perimeter


                    // -----------------------------
                    // Верхняя сторона
                    // -----------------------------

                    if (distance <= straightWidth) {

                        return Offset(
                            left + cornerRadius + distance,
                            top
                        )
                    }

                    distance -= straightWidth


                    // -----------------------------
                    // Верхний правый угол
                    // -----------------------------

                    if (distance <= cornerLength) {

                        val angle =
                            -PI.toFloat() / 2f +
                                    distance / cornerLength *
                                    (PI.toFloat() / 2f)

                        return Offset(
                            right - cornerRadius +
                                    cos(angle) * cornerRadius,

                            top + cornerRadius +
                                    sin(angle) * cornerRadius
                        )
                    }

                    distance -= cornerLength


                    // -----------------------------
                    // Правая сторона
                    // -----------------------------

                    if (distance <= straightHeight) {

                        return Offset(
                            right,
                            top + cornerRadius + distance
                        )
                    }

                    distance -= straightHeight


                    // -----------------------------
                    // Нижний правый угол
                    // -----------------------------

                    if (distance <= cornerLength) {

                        val angle =
                            distance / cornerLength *
                                    (PI.toFloat() / 2f)

                        return Offset(
                            right - cornerRadius +
                                    cos(angle) * cornerRadius,

                            bottom - cornerRadius +
                                    sin(angle) * cornerRadius
                        )
                    }

                    distance -= cornerLength


                    // -----------------------------
                    // Нижняя сторона
                    // -----------------------------

                    if (distance <= straightWidth) {

                        return Offset(
                            right - cornerRadius - distance,
                            bottom
                        )
                    }

                    distance -= straightWidth


                    // -----------------------------
                    // Нижний левый угол
                    // -----------------------------

                    if (distance <= cornerLength) {

                        val angle =
                            PI.toFloat() / 2f +
                                    distance / cornerLength *
                                    (PI.toFloat() / 2f)

                        return Offset(
                            left + cornerRadius +
                                    cos(angle) * cornerRadius,

                            bottom - cornerRadius +
                                    sin(angle) * cornerRadius
                        )
                    }

                    distance -= cornerLength


                    // -----------------------------
                    // Левая сторона
                    // -----------------------------

                    if (distance <= straightHeight) {

                        return Offset(
                            left,
                            bottom - cornerRadius - distance
                        )
                    }

                    distance -= straightHeight


                    // -----------------------------
                    // Верхний левый угол
                    // -----------------------------

                    val angle =
                        PI.toFloat() +
                                distance / cornerLength *
                                (PI.toFloat() / 2f)

                    return Offset(
                        left + cornerRadius +
                                cos(angle) * cornerRadius,

                        top + cornerRadius +
                                sin(angle) * cornerRadius
                    )
                }


                // =================================================
                // ЦВЕТ
                // =================================================

                fun colorAt(position: Float): Color {

                    val normalized =
                        ((position % 1f) + 1f) % 1f

                    val yellow =
                        Color(0xFFFFD36A)

                    val violet =
                        Color(0xFFB56CFF)

                    return Color(
                        red =
                        yellow.red +
                                (violet.red - yellow.red) *
                                normalized,

                        green =
                        yellow.green +
                                (violet.green - yellow.green) *
                                normalized,

                        blue =
                        yellow.blue +
                                (violet.blue - yellow.blue) *
                                normalized,

                        alpha = 1f
                    )
                }


                // =================================================
                // ОСНОВНАЯ РАМКА
                // =================================================

                val baseSegments = 240

                for (i in 0 until baseSegments) {

                    val startPosition =
                        i.toFloat() / baseSegments

                    val endPosition =
                        (i + 1).toFloat() / baseSegments

                    drawLine(

                        color = colorAt(
                            (startPosition + endPosition) / 2f
                        ).copy(alpha = 0.28f),

                        start = pointOnPerimeter(
                            startPosition
                        ),

                        end = pointOnPerimeter(
                            endPosition
                        ),

                        strokeWidth = 2.dp.toPx()
                    )
                }


                // =================================================
                // БЕГУЩИЙ СВЕТ
                // =================================================

                // Длина светящегося следа
                val runnerLength = 0.18f

                // Количество маленьких сегментов
                val runnerSegments = 70


                for (i in 0 until runnerSegments) {

                    val segmentStart =
                        progress -
                                runnerLength +
                                runnerLength *
                                i.toFloat() /
                                runnerSegments

                    val segmentEnd =
                        progress -
                                runnerLength +
                                runnerLength *
                                (i + 1).toFloat() /
                                runnerSegments


                    // Градиент яркости внутри следа

                    val brightness =
                        i.toFloat() / runnerSegments


                    val alpha =
                        0.08f +
                                brightness * 0.85f


                    // Широкое мягкое свечение

                    drawLine(

                        color = colorAt(
                            segmentEnd
                        ).copy(alpha = alpha),

                        start = pointOnPerimeter(
                            segmentStart
                        ),

                        end = pointOnPerimeter(
                            segmentEnd
                        ),

                        strokeWidth = 8.dp.toPx()
                    )


                    // Более яркая центральная линия

                    drawLine(

                        color = colorAt(
                            segmentEnd
                        ).copy(alpha = alpha),

                        start = pointOnPerimeter(
                            segmentStart
                        ),

                        end = pointOnPerimeter(
                            segmentEnd
                        ),

                        strokeWidth = 3.dp.toPx()
                    )
                }


                // =================================================
                // ЯРКАЯ ТОЧКА НА ГОЛОВЕ СЛЕДА
                // =================================================

                val head =
                    pointOnPerimeter(progress)


                drawCircle(

                    color = Color.White.copy(
                        alpha = 0.95f
                    ),

                    radius = 4.dp.toPx(),

                    center = head
                )


                // Мягкое свечение вокруг точки

                drawCircle(

                    color = colorAt(progress).copy(
                        alpha = 0.22f
                    ),

                    radius = 13.dp.toPx(),

                    center = head
                )
            }
        }


        // =========================================================
        // ТЕКСТ
        // =========================================================

        Column(

            modifier = Modifier.fillMaxSize(),

            horizontalAlignment =
            Alignment.CenterHorizontally,

            verticalArrangement =
            Arrangement.Center
        ) {


            // -----------------------------------------
            // НЕИЗВЕСТНОЕ СОЕДИНЕНИЕ
            // -----------------------------------------

            if (stage >= 2) {

                Text(
                    text = "НЕИЗВЕСТНОЕ СОЕДИНЕНИЕ",
                    color = Color(0xFFE0D6C5),
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }


            // -----------------------------------------
            // УСТАНОВЛЕНИЕ СВЯЗИ
            // -----------------------------------------

            if (stage >= 3) {

                Text(
                    text = "УСТАНОВЛЕНИЕ СВЯЗИ...",
                    color = Color(0xFFB8A7D9),
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }


            // -----------------------------------------
            // СОЕДИНЕНИЕ УСТАНОВЛЕНО
            // -----------------------------------------

            if (stage >= 4) {

                Text(
                    text = "СОЕДИНЕНИЕ УСТАНОВЛЕНО",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}