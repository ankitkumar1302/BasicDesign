// FavoritesViewModel.kt
package com.viprab.basicdesign.ui.favorites

import androidx.lifecycle.ViewModel
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModel : ViewModel() {
    // Using sampleBooks here as well.
    private val _favoritesBooks = MutableStateFlow(sampleBooks.filter { it.likes > 250 })
    val favoritesBooks: StateFlow<List<Book>> = _favoritesBooks.asStateFlow()
}
