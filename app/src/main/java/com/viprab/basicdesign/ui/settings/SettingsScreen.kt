package com.viprab.basicdesign.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF252534))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsHeader()

        SettingsToggleItem(
            icon = Icons.Outlined.DarkMode,
            title = "Dark Mode",
            description = "Use the dark color theme"
        )
        SettingsToggleItem(
            icon = Icons.Outlined.Notifications,
            title = "Notifications",
            description = "Receive updates about new books"
        )
        SettingsToggleItem(
            icon = Icons.Outlined.Download,
            title = "Download over Wi‑Fi only",
            description = "Save mobile data"
        )
    }
}

@Composable
private fun SettingsHeader() {
    Text(
        text = "Settings",
        style = MaterialTheme.typography.headlineSmall,
        color = Color.White,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Text(
        text = "Personalize your experience",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.LightGray
    )
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    description: String
) {
    val checkedState = remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = Color.White)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(text = title, color = Color.White)
                Text(text = description, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }
            Switch(checked = checkedState.value, onCheckedChange = { checkedState.value = it })
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}

