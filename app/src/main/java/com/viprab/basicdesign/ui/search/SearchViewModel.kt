package com.viprab.basicdesign.ui.search

import androidx.lifecycle.ViewModel
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {
    private val allBooks: List<Book> = sampleBooks

    private val _filteredBooks = MutableStateFlow(allBooks)
    val filteredBooks: StateFlow<List<Book>> = _filteredBooks.asStateFlow()

    fun updateQuery(query: String) {
        val normalized = query.trim().lowercase()
        if (normalized.isBlank()) {
            _filteredBooks.value = allBooks
        } else {
            _filteredBooks.value = allBooks.filter { book ->
                book.title.lowercase().contains(normalized) ||
                    book.author.lowercase().contains(normalized)
            }
        }
    }
}

