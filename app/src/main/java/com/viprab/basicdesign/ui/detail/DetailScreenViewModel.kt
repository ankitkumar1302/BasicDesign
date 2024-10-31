package com.viprab.basicdesign.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel : ViewModel() {

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book

    // Function to load book details using the bookId
    fun loadBook(bookId: Int) {
        viewModelScope.launch {
            _book.value = sampleBooks.find { it.id == bookId }
        }
    }
}
