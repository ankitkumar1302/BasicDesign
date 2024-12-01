package com.viprab.basicdesign.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viprab.basicdesign.components.BookItemCard
import com.viprab.basicdesign.model.Book

@Composable
fun HomeScreen(
    onBookClick: (Book) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val books = viewModel.books.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF252534))
                )
            )
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(books) { book ->
                var isClicked by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(if (isClicked) 0.95f else 1f)

                BookItemCard(
                    book = book,
                    modifier = Modifier
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .clickable {
                            isClicked = true
                            onBookClick(book)
                            isClicked = false
                        },
                    onClick = {
                        onBookClick(book)
                    }
                )
            }
        }
    }
}