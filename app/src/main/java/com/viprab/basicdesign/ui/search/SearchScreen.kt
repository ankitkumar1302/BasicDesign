package com.viprab.basicdesign.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viprab.basicdesign.components.BookItemCard
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks

@Composable
fun SearchScreen(onBookClick: (Book) -> Unit = {}) {
    val queryState = remember { mutableStateOf("") }
    val filtered = remember(queryState.value) {
        val q = queryState.value.trim().lowercase()
        if (q.isEmpty()) emptyList() else sampleBooks.filter {
            it.title.lowercase().contains(q) || it.author.lowercase().contains(q)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF252534))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = queryState.value,
            onValueChange = { queryState.value = it },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search books or authors") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (queryState.value.isBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Start typing to search",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else if (filtered.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No results",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered) { book ->
                    BookItemCard(book = book, onClick = { onBookClick(book) })
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}

