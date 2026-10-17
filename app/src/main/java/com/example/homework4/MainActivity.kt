package com.example.homework4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// 1. Структура данных
data class AppInfo(
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String
)

// 2. Имитация получения данных с сервера
val appsFromServer = listOf(
    AppInfo(
        name = "ВКонтакте",
        description = "Социальная сеть, мессенджер и звонки.",
        category = "Социальные сети",
        iconUrl = "https://picsum.photos/200?random=1"
    ),
    AppInfo(
        name = "VK Музыка",
        description = "Миллионы треков и плейлисты.",
        category = "Музыка",
        iconUrl = "https://picsum.photos/200?random=2"
    ),
    AppInfo(
        name = "Шаги",
        description = "Считайте шаги и участвуйте в вызовах.",
        category = "Здоровье",
        iconUrl = "https://picsum.photos/200?random=3"
    ),
    AppInfo(
        name = "Почта Mail.ru",
        description = "Быстрый и безопасный почтовый клиент.",
        category = "Инструменты",
        iconUrl = "https://picsum.photos/200?random=4"
    )
)

// 3. Главная Activity
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AppListScreen()
                }
            }
        }
    }
}

// 4. Экран списка приложений
@Composable
fun AppListScreen() {
    val appsList = remember { appsFromServer }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Тулбар
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://picsum.photos/200?random=10",
                contentDescription = "Иконка приложения",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Список приложений",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Список занимает оставшееся место
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(appsList) { app ->
                AppItemRow(app = app)
            }
        }
    }
}

// 5. Строка приложения
@Composable
fun AppItemRow(app: AppInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = app.iconUrl,
            contentDescription = "Иконка ${app.name}",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = app.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = app.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Text(
                text = app.category,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
