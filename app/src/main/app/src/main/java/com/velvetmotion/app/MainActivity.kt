package com.velvetmotion.app

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.PrintWriter
import java.io.StringWriter

var crashError: String? = null

// Класс, который ловит вылеты
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            val sw = StringWriter()
            throwable.printStackTrace(PrintWriter(sw))
            crashError = sw.toString()
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Если была ошибка - показываем её текст на белом экране
                if (crashError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "ОШИБКА:\n\n$crashError",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    // Если ошибок нет - показываем анимацию
                    VelvetScreen()
                }
            }
        }
    }
}

@Composable
fun VelvetScreen() {
    var isVisible by remember { mutableStateOf(false) }

    // Используем более безопасный способ анимации
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "alpha"
    )

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
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

            if (isVisible || alpha > 0.01f) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .alpha(alpha)
                        .scale(scale)
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
