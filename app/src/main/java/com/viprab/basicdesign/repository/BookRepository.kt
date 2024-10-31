package com.viprab.basicdesign.repository

import com.viprab.basicdesign.model.Book

class BookRepository {
    fun getBooks(): List<Book> {
        // Mock data. Replace with a real data source as needed.
        return listOf(
            Book(
                1,
                "Tome of the Sun",
                "Author A",
                "url_to_cover_image",
                "Book description here",
                4.9,
                1224,
                94000,
                280
            ),
            // Add more book data as required.
        )
    }

    fun getBookById(id: Int): Book? {
        return getBooks().find { it.id == id }
    }
}
