// SlideDrawer.kt

package com.viprab.basicdesign.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viprab.basicdesign.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SlideDrawer(
    drawerState: DrawerState,
    onItemClick: (String) -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerWidth = 280.dp

    // Configure easing functions for smoother animations with explicit types
    val offset: Dp by animateDpAsState(
        targetValue = if (drawerState.isOpen) drawerWidth else 0.dp,
        animationSpec = tween(durationMillis = 400, easing = androidx.compose.animation.core.FastOutSlowInEasing)
    )
    val scale: Float by animateFloatAsState(
        targetValue = if (drawerState.isOpen) 0.85f else 1f,
        animationSpec = tween(durationMillis = 400, easing = androidx.compose.animation.core.FastOutSlowInEasing)
    )

    val overlayColor = Color(0xFF1E1E2C)

    Box(modifier = Modifier.fillMaxSize()) {
        // Overlay color behind the main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (drawerState.isOpen) overlayColor else Color(0xFF1E1E2C))
        )

        // Main content with offset, scaling animation, and dismiss-on-click action
        content(
            Modifier
                .offset(x = offset)
                .scale(scale)
                .clip(RoundedCornerShape(if (drawerState.isOpen) 16.dp else 0.dp))
                .background(Color.Transparent)
                .clickable(
                    enabled = drawerState.isOpen,
                    onClick = { scope.launch { drawerState.close() } }
                ) // Close drawer on click outside
        )

        // Drawer content, only visible when open
        if (drawerState.isOpen) {
            Column(
                modifier = Modifier
                    .width(drawerWidth)
                    .fillMaxHeight()
                    .background(Color(0xFF1F1F2A))
                    .padding(16.dp)
            ) {
                DrawerHeader()
                Spacer(modifier = Modifier.height(24.dp))
                DrawerItem(icon = Icons.Default.Home, label = "Home") {
                    closeDrawer(scope, drawerState, "home", onItemClick)
                }
                DrawerItem(icon = Icons.Default.Person, label = "Profile") {
                    closeDrawer(scope, drawerState, "profile", onItemClick)
                }
                DrawerItem(icon = Icons.Default.Logout, label = "Logout") {
                    closeDrawer(scope, drawerState, "logout", onItemClick)
                }
            }
        }
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Ankit", fontSize = 20.sp, color = Color.White)
    }
}

@Composable
fun DrawerItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .background(Color(0x33212121), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, fontSize = 16.sp, color = Color.White)
    }
}

private fun closeDrawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    destination: String,
    onItemClick: (String) -> Unit
) {
    scope.launch {
        drawerState.close()
        onItemClick(destination)
    }
}
