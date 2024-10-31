package com.viprab.basicdesign.ui.home

import androidx.lifecycle.ViewModel
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    // StateFlow to hold and observe the list of books
    private val _books = MutableStateFlow<List<Book>>(sampleBooks)
    val books: StateFlow<List<Book>> = _books // Publicly exposed as immutable StateFlow
}
