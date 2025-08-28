package com.viprab.basicdesign.ui.authors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viprab.basicdesign.components.BookItemCard
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks

@Composable
fun AuthorBooksScreen(author: String, onBookClick: (Book) -> Unit = {}) {
    val booksByAuthor = sampleBooks.filter { it.author == author }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF252534))
                )
            )
            .padding(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = author,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
            )
        }
        items(booksByAuthor) { book ->
            BookItemCard(book = book, onClick = { onBookClick(book) })
        }
    }
}

@Preview
@Composable
private fun AuthorBooksScreenPreview() {
    AuthorBooksScreen(author = "J.R.R. Tolkien")
}

