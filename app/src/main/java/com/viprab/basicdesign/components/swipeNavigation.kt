package com.viprab.basicdesign.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.absoluteValue

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.lerp

fun Modifier.swipeNavigation(
    navController: NavController,
    routes: List<String>,
    threshold: Dp = 50.dp
) = composed {
    val density = LocalDensity.current
    var offsetX by remember { mutableFloatStateOf(0f) }
    val thresholdPx = with(density) { threshold.toPx() }
    var isNavigating by remember { mutableStateOf(false) }

    pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragStart = { offsetX = 0f },
            onDragEnd = {
                val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""
                val currentIndex = routes.indexOf(currentRoute)

                // Check if swipe distance exceeds threshold and we can navigate
                if (offsetX.absoluteValue > thresholdPx && !isNavigating) {
                    isNavigating = true
                    when {
                        offsetX > 0 && currentIndex > 0 -> {
                            navController.navigate(routes[currentIndex - 1]) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                        offsetX < 0 && currentIndex < routes.lastIndex -> {
                            navController.navigate(routes[currentIndex + 1]) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                    isNavigating = false
                }
            },
            onHorizontalDrag = { change, dragAmount ->
                offsetX += dragAmount
                change.consume()
            }
        )
    }
}


@Composable
fun SwipeableContent(
    navController: NavController,
    routes: List<String>,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipeNavigation(
                navController = navController,
                routes = routes
            )
    ) {
        content()
    }
}