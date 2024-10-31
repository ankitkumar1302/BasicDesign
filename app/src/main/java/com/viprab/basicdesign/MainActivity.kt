package com.viprab.basicdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.viprab.basicdesign.components.CustomBottomNavigationBar
import com.viprab.basicdesign.components.SlideDrawer
import com.viprab.basicdesign.components.swipeNavigation
import com.viprab.basicdesign.navigation.NavigationHost
import com.viprab.basicdesign.ui.theme.BasicDesignTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicDesignTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberAnimatedNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val commonBarColor = Color(0xCC1E1E2C) // Common bar color
    val mainRoutes = listOf("home", "genres", "favorites") // Define main routes for swipe navigation

    SlideDrawer(
        drawerState = drawerState,
        onItemClick = { destination ->
            navController.navigate(destination) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
        }
    ) { contentModifier ->
        Scaffold(
            modifier = contentModifier,
            topBar = {
                TopAppBar(
                    title = { Text(text = "My Novel") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = commonBarColor,
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Drawer",
                                tint = Color.White
                            )
                        }
                    },
                    modifier = Modifier.shadow(4.dp)
                )
            },
            bottomBar = {
                CustomBottomNavigationBar(
                    navController = navController,
                    containerColor = commonBarColor
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .swipeNavigation(navController, mainRoutes) // Enable swipe navigation across main routes
            ) {
                NavigationHost(navController = navController)
            }
        }
    }
}
