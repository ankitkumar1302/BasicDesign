package com.viprab.basicdesign.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
            name = "Ankit",
            email = "ankit@google.com",
            phone = "+123 456 7890",
            address = "123 Street, City, Country",
            memberSince = "January 2069",
            profileImage = com.viprab.basicdesign.R.drawable.profile // Replace with actual image resource
        )
    )

    // Public immutable state
    val profileState: StateFlow<ProfileData> = _profileState

}
