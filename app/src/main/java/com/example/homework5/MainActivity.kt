package com.example.vkeducation // Замените на имя вашего пакета, если отличается

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// ==========================================
// 1. ТОЧКА ВХОДА (ACTIVITY)
// ==========================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// ==========================================
// 2. МОДЕЛЬ ДАННЫХ И ХАРДКОД
// ==========================================

data class AppItem(
    val id: String,
    val name: String,
    val category: String,
    val rating: Double
)

object FakeData {
    // 9 приложений для Витрины (3 страницы по 3 приложения)
    val showcaseApps = listOf(
        AppItem("1", "ВКонтакте", "Социальные сети", 4.7),
        AppItem("2", "VK Музыка", "Музыка", 4.5),
        AppItem("3", "Шаги", "Здоровье", 4.3),

        AppItem("4", "VK Видео", "Развлечения", 4.6),
        AppItem("5", "Почта Mail.ru", "Инструменты", 4.4),
        AppItem("6", "Облако", "Продуктивность", 4.5),

        AppItem("7", "Сферум", "Образование", 4.2),
        AppItem("8", "VK Мессенджер", "Общение", 4.6),
        AppItem("9", "Маруся", "Помощники", 4.1)
    )

    // Список приложений для главного экрана (из прошлого ДЗ)
    val mainAppList = listOf(
        AppItem("vk", "ВКонтакте", "Социальные сети", 4.7),
        AppItem("music", "VK Музыка", "Музыка", 4.5),
        AppItem("video", "VK Видео", "Развлечения", 4.6)
    )
}

// ==========================================
// 3. UI КОМПОНЕНТЫ И ВИТРИНА
// ==========================================

// Переиспользуемая верстка элемента (из списка приложений)
@Composable
fun AppRowItem(
    app: AppItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // Иконка-заглушка
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0077FF).copy(alpha = 0.1f))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1fr)) {
            Text(text = app.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = app.category, fontSize = 14.sp, color = Color.Gray)
        }
        Text(
            text = "★ ${app.rating}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

// UI Блок «Витрина» с постраничным скроллом (фиксированно 3 страницы по 3 элемента)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowcaseBlock(
    apps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val pageCount = 3
    val itemsPerPage = 3
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Витрина",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Column(modifier = Modifier.fillMaxWidth()) {
                val startIndex = page * itemsPerPage
                val endIndex = minOf(startIndex + itemsPerPage, apps.size)
                val pageItems = apps.subList(startIndex, endIndex)

                pageItems.forEach { app ->
                    AppRowItem(
                        app = app,
                        onClick = { onAppClick(app) }
                    )
                }
            }
        }
    }
}

// ==========================================
// 4. ЭКРАНЫ ПРИЛОЖЕНИЯ
// ==========================================

// Экран 1: Список приложений
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    onAppClick: (String) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Список приложений") }) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(FakeData.mainAppList) { app ->
                AppRowItem(
                    app = app,
                    onClick = { onAppClick(app.id) }
                )
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 0.5.dp)
            }
        }
    }
}

// Экран 2: Карточка приложения (Детали)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    appId: String?,
    onShowcaseAppClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Карточка приложения") }) }
    ) { paddingValues ->
        // LazyColumn используется, чтобы Витрина была строго в самом низу и всё скроллилось
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Верхняя часть: Контент карточки приложения из практики
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Приложение ID: $appId",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Здесь отображается основная верстка карточки приложения из вашего практического репозитория.",
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            // Нижняя часть: UI блок «Витрина»
            item {
                ShowcaseBlock(
                    apps = FakeData.showcaseApps,
                    onAppClick = {
                        // По клику на витрину переходим на список приложений
                        onShowcaseAppClick()
                    }
                )
            }
        }
    }
}

// ==========================================
// 5. НАВИГАЦИЯ (JETPACK COMPOSE NAVIGATION)
// ==========================================

sealed class Screen(val route: String) {
    object AppList : Screen("app_list")
    object AppDetails : Screen("app_details/{appId}") {
        fun createRoute(appId: String) = "app_details/$appId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.AppList.route
    ) {
        // Клик по элементу списка -> переход в Карточку приложения
        composable(route = Screen.AppList.route) {
            AppListScreen(
                onAppClick = { appId ->
                    navController.navigate(Screen.AppDetails.createRoute(appId))
                }
            )
        }

        // Клик по любому элементу «Витрины» -> возврат на Список приложений
        composable(
            route = Screen.AppDetails.route,
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId")

            AppDetailsScreen(
                appId = appId,
                onShowcaseAppClick = {
                    navController.navigate(Screen.AppList.route) {
                        // Очищаем стек навигации до списка, чтобы не копить экраны по кругу
                        popUpTo(Screen.AppList.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
