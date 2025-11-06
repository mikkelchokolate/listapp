package com.example.listapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true

        setContent {
            ListApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListApp() {
    var itemsCount by rememberSaveable { mutableIntStateOf(0) }

    val configuration = LocalConfiguration.current
    val columns = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 3
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { itemsCount++ }) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding() + 8.dp,   // верхний зазор
                bottom = innerPadding.calculateBottomPadding() + 72.dp // нижний зазор
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            items(count = itemsCount) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f) // квадраты
                        .background(if (index % 2 == 0) Color.Red else Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = index.toString(),
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}