package com.viprab.basicdesign.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileData(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val memberSince: String,
    val profileImage: Int
)

class ProfileViewModel : ViewModel() {
    // Private mutable state
    private val _profileState = MutableStateFlow(
        ProfileData(
            name = "Ankit Tiwari",
            email = "ankit.tiwari@google.com",
            phone = "+123 456 7890",
            address = "123 Street, City, Country",
            memberSince = "January 2069",
            profileImage = com.viprab.basicdesign.R.drawable.profile // Replace with actual image resource
        )
    )

    // Public immutable state
    val profileState: StateFlow<ProfileData> = _profileState

    // Simulate loading new profile data
    fun loadProfile() {
        viewModelScope.launch {
            // Simulate a delay or API call to fetch profile data
            _profileState.emit(
                ProfileData(
                    name = "John Doe",
                    email = "john.doe@example.com",
                    phone = "+987 654 3210",
                    address = "456 Avenue, City, Country",
                    memberSince = "March 2023",
                    profileImage = com.viprab.basicdesign.R.drawable.profile // Replace as needed
                )
            )
        }
    }
}
