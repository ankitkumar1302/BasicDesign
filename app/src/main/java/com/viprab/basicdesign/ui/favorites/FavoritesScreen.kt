package com.viprab.basicdesign.ui.favorites

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viprab.basicdesign.components.BookItemCard
import com.viprab.basicdesign.model.Book

@Composable
fun FavoritesScreen(
    onBookClick: (Book) -> Unit = {}
) {
    // Hardcoded list of favorite books
    val books = listOf(
        Book(
            id = 1,
            title = "To Kill a Mockingbird",
            author = "Harper Lee",
            coverImageUrl = "https://i.pinimg.com/736x/e7/ed/b3/e7edb3bd3c80e66792e8d94e4c255a3d.jpg",
            rating = 4.8,
            reviewsCount = 1234,
            likes = 300,
            description = "A classic novel of morality and justice.",
            shares = 80
        ),
        Book(
            id = 2,
            title = "1984",
            author = "George Orwell",
            coverImageUrl = "https://i.pinimg.com/736x/55/b7/7a/55b77a1a24f101fbc7efe20b181262a5.jpg",
            rating = 4.6,
            reviewsCount = 1400,
            likes = 400,
            description = "A dystopian tale of surveillance and control.",
            shares = 100
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF252534))
                )
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        if (books.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your Favorites List is Empty",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Favorites List
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Space between cards
            ) {
                items(books) { book ->
                    BookItemCard(
                        book = book,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp),
                        onClick = { onBookClick(book) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen()
}
