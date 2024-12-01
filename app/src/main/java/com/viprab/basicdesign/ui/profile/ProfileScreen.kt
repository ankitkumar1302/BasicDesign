package com.viprab.basicdesign.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfilePage(viewModel: ProfileViewModel = viewModel()) {
    val profileData = viewModel.profileState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E2C))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Image
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        ) {
            Image(
                painter = painterResource(id = profileData.profileImage),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = profileData.name, fontSize = 24.sp, color = Color.White)
        Text(text = profileData.email, fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Additional Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2A2A3A), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            ProfileInfoItem(label = "Phone", value = profileData.phone)
            ProfileInfoItem(label = "Address", value = profileData.address)
            ProfileInfoItem(label = "Member Since", value = profileData.memberSince)
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
@Preview
fun ProfilePagePreview() {
    ProfilePage()
}
