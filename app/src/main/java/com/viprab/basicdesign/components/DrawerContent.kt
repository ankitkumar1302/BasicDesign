package com.viprab.basicdesign.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF33334D))
            .padding(16.dp)
    ) {
        Text(
            "Home",
            color = Color.White,
            modifier = Modifier
                .clickable { onItemClick("home") }
                .padding(8.dp)
        )
        Text(
            "Genres",
            color = Color.White,
            modifier = Modifier
                .clickable { onItemClick("genres") }
                .padding(8.dp)
        )
        Text(
            "Favorites",
            color = Color.White,
            modifier = Modifier
                .clickable { onItemClick("favorites") }
                .padding(8.dp)
        )
        Text(
            "Settings",
            color = Color.White,
            modifier = Modifier
                .clickable { onItemClick("settings") }
                .padding(8.dp)
        )
    }
}
