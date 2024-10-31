// GenresViewModel.kt
package com.viprab.basicdesign.ui.genres

import androidx.lifecycle.ViewModel
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GenresViewModel : ViewModel() {
    // For this example, we can use the same sampleBooks for simplicity.
    private val _genresBooks = MutableStateFlow(sampleBooks)
    val genresBooks: StateFlow<List<Book>> = _genresBooks.asStateFlow()
}
