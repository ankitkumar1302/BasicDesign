package com.viprab.basicdesign.ui.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viprab.basicdesign.ui.theme.*

@Composable
fun SettingsScreen() {
    var darkThemeEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var dailyGoalReminder by remember { mutableStateOf(false) }
    var newBookAlerts by remember { mutableStateOf(true) }
    var autoSync by remember { mutableStateOf(true) }
    var downloadOverWifi by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PrimaryDark, PrimaryLight)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Settings Header
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Appearance Section
            SettingsSection(
                title = "Appearance",
                icon = Icons.Default.Palette
            ) {
                SettingsToggleItem(
                    title = "Dark Theme",
                    subtitle = "Enable dark mode for better reading at night",
                    icon = Icons.Default.DarkMode,
                    checked = darkThemeEnabled,
                    onCheckedChange = { darkThemeEnabled = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notifications Section
            SettingsSection(
                title = "Notifications",
                icon = Icons.Default.Notifications
            ) {
                SettingsToggleItem(
                    title = "Push Notifications",
                    subtitle = "Receive notifications about your reading",
                    icon = Icons.Default.NotificationsActive,
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                
                AnimatedVisibility(visible = notificationsEnabled) {
                    Column {
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = TextSecondary.copy(alpha = 0.2f)
                        )
                        SettingsToggleItem(
                            title = "Daily Goal Reminder",
                            subtitle = "Get reminded about your daily reading goal",
                            icon = Icons.Default.Schedule,
                            checked = dailyGoalReminder,
                            onCheckedChange = { dailyGoalReminder = it }
                        )
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = TextSecondary.copy(alpha = 0.2f)
                        )
                        SettingsToggleItem(
                            title = "New Book Alerts",
                            subtitle = "Notify when new books are added",
                            icon = Icons.Default.BookmarkAdd,
                            checked = newBookAlerts,
                            onCheckedChange = { newBookAlerts = it }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Data & Storage Section
            SettingsSection(
                title = "Data & Storage",
                icon = Icons.Default.Storage
            ) {
                SettingsToggleItem(
                    title = "Auto-Sync",
                    subtitle = "Automatically sync your reading progress",
                    icon = Icons.Default.Sync,
                    checked = autoSync,
                    onCheckedChange = { autoSync = it }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = TextSecondary.copy(alpha = 0.2f)
                )
                SettingsToggleItem(
                    title = "Download Over Wi-Fi Only",
                    subtitle = "Save mobile data by downloading only on Wi-Fi",
                    icon = Icons.Default.Wifi,
                    checked = downloadOverWifi,
                    onCheckedChange = { downloadOverWifi = it }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = TextSecondary.copy(alpha = 0.2f)
                )
                SettingsClickableItem(
                    title = "Clear Cache",
                    subtitle = "Free up storage space",
                    icon = Icons.Default.DeleteSweep,
                    onClick = { /* Handle clear cache */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Account Section
            SettingsSection(
                title = "Account",
                icon = Icons.Default.AccountCircle
            ) {
                SettingsClickableItem(
                    title = "Edit Profile",
                    subtitle = "Update your profile information",
                    icon = Icons.Default.Edit,
                    onClick = { /* Navigate to edit profile */ }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = TextSecondary.copy(alpha = 0.2f)
                )
                SettingsClickableItem(
                    title = "Change Password",
                    subtitle = "Update your account password",
                    icon = Icons.Default.Lock,
                    onClick = { /* Navigate to change password */ }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = TextSecondary.copy(alpha = 0.2f)
                )
                SettingsClickableItem(
                    title = "Sign Out",
                    subtitle = "Sign out from your account",
                    icon = Icons.Default.Logout,
                    onClick = { /* Handle sign out */ },
                    tint = ErrorColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // About Section
            SettingsSection(
                title = "About",
                icon = Icons.Default.Info
            ) {
                SettingsClickableItem(
                    title = "Terms of Service",
                    subtitle = "Read our terms and conditions",
                    icon = Icons.Default.Description,
                    onClick = { /* Open terms */ }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = TextSecondary.copy(alpha = 0.2f)
                )
                SettingsClickableItem(
                    title = "Privacy Policy",
                    subtitle = "Learn how we protect your data",
                    icon = Icons.Default.PrivacyTip,
                    onClick = { /* Open privacy policy */ }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = TextSecondary.copy(alpha = 0.2f)
                )
                SettingsClickableItem(
                    title = "App Version",
                    subtitle = "1.0.0",
                    icon = Icons.Default.Android,
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AccentColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            content()
        }
    }
}

@Composable
private fun SettingsToggleItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val scale by animateFloatAsState(if (checked) 1f else 0.95f, label = "")
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (checked) AccentColor.copy(alpha = 0.2f)
                    else Color(0xFF2D2B40)
                )
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) AccentColor else TextSecondary,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        
        CustomSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsClickableItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    tint: Color = AccentColor
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "")
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(tint.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbColor by animateColorAsState(
        targetValue = if (checked) AccentColor else Color(0xFF666666),
        label = ""
    )
    val trackColor by animateColorAsState(
        targetValue = if (checked) AccentColor.copy(alpha = 0.3f) else Color(0xFF2D2B40),
        label = ""
    )
    
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = thumbColor,
            checkedTrackColor = trackColor,
            uncheckedThumbColor = thumbColor,
            uncheckedTrackColor = trackColor,
            checkedBorderColor = Color.Transparent,
            uncheckedBorderColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedVisibility(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(),
        exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkVertically()
    ) {
        content()
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    BasicDesignTheme {
        SettingsScreen()
    }
}