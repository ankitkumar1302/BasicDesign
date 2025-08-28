package com.viprab.basicdesign.ui.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsState(
    val darkMode: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val cellularData: Boolean = false
)

class SettingsViewModel : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    fun toggleDarkMode() {
        _settingsState.value = _settingsState.value.copy(darkMode = !_settingsState.value.darkMode)
    }

    fun toggleNotifications() {
        _settingsState.value = _settingsState.value.copy(
            notificationsEnabled = !_settingsState.value.notificationsEnabled
        )
    }

    fun toggleCellularData() {
        _settingsState.value = _settingsState.value.copy(cellularData = !_settingsState.value.cellularData)
    }
}

