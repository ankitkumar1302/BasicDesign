package com.viprab.basicdesign.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun CustomBottomNavigationBar(
    navController: NavController,
    containerColor: Color = Color(0xFF1E1E2C)
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = containerColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry.value?.destination?.route

                BottomNavItem(
                    navController = navController,
                    route = "home",
                    icon = Icons.Filled.Home,
                    label = "Home",
                    isSelected = currentDestination == "home"
                )
                BottomNavItem(
                    navController = navController,
                    route = "genres",
                    icon = Icons.Filled.Book,
                    label = "Genres",
                    isSelected = currentDestination == "genres"
                )
                BottomNavItem(
                    navController = navController,
                    route = "favorites",
                    icon = Icons.Filled.Favorite,
                    label = "Favorites",
                    isSelected = currentDestination == "favorites"
                )
            }
        }
    }
}

@Composable
fun BottomNavItem(
    navController: NavController,
    route: String,
    icon: ImageVector,
    label: String,
    isSelected: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundColor = if (isSelected) {
        Color(0xFF33334D).copy(alpha = 0.6f)
    } else {
        Color.Transparent
    }

    val contentColor = if (isSelected) Color.White else Color.Gray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (!isSelected) {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBottomNavigationBarPreview() {
    CustomBottomNavigationBar(navController = NavController(context = LocalContext.current))
}
