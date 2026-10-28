package com.velvetmotion.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                VelvetScreen()
            }
        }
    }
}

@Composable
fun VelvetScreen() {
    // Состояние: показана карточка или нет
    var isVisible by remember { mutableStateOf(false) }

    // Наша премиальная пружинная анимация
    val springSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy, // Легкий отскок
        stiffness = Spring.StiffnessMediumLow        // Плавность
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)), // Темный фон в стиле OriginOS
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Velvet Motion",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            // Анимируемый блок
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = springSpec) + scaleIn(animationSpec = springSpec, initialScale = 0.8f),
                exit = fadeOut(animationSpec = springSpec) + scaleOut(animationSpec = springSpec, targetScale = 0.8f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color(0xFF2C2C2C), RoundedCornerShape(24.dp))
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Плавная анимация работает!",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }

            // Кнопка управления
            Button(
                onClick = { isVisible = !isVisible },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (isVisible) "Скрыть" else "Показать анимацию",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
