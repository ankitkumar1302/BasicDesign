package com.viprab.basicdesign.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val coverImageUrl: String = "", // Use an empty string if no URL
    val description: String,
    val rating: Double,
    val reviewsCount: Int,
    val likes: Int,
    val shares: Int
)
val sampleBooks = listOf(
    Book(
        id = 1,
        title = "The Great Gatsby",
        author = "F. Scott Fitzgerald",
        coverImageUrl = "https://i.pinimg.com/736x/6b/36/d6/6b36d64fc442a90c6b4c47336d895be1.jpg", // Example URL
        description = "The Great Gatsby is a novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, near New York City, it depicts first-person narrator Nick Carraway's interactions with millionaire Jay Gatsby and Gatsby's obsession to reunite with Daisy Buchanan.",
        rating = 4.5,
        reviewsCount = 150,
        likes = 250,
        shares = 60
    ),
    Book(
        id = 2,
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        coverImageUrl = "https://i.pinimg.com/564x/01/5e/1b/015e1b3312ec674f6f95b6fb95ea35f8.jpg", // Will use drawable fallback if empty
        description = "A novel about the serious issues of rape and racial inequality, narrated by young Scout Finch in the 1930s. The book is renowned for its warmth and humor, despite dealing with serious social issues.",
        rating = 4.8,
        reviewsCount = 200,
        likes = 300,
        shares = 80
    ),
    Book(
        id = 3,
        title = "1984",
        author = "George Orwell",
        coverImageUrl = "https://i.pinimg.com/736x/a5/eb/bd/a5ebbd187a4263cd2e934318da6c1fbc.jpg", // Example URL
        description = "A dystopian social science fiction novel and cautionary tale about the dangers of totalitarianism, 1984 explores themes of surveillance, control, and repression.",
        rating = 4.6,
        reviewsCount = 250,
        likes = 400,
        shares = 100
    ),
    Book(
        id = 4,
        title = "Pride and Prejudice",
        author = "Jane Austen",
        coverImageUrl = "https://i.pinimg.com/736x/e3/78/10/e3781050266fee807dc4f08a5ef7f34b.jpg", // Example URL
        description = "A romantic novel of manners, the story follows Elizabeth Bennet as she navigates issues of morality, marriage, and social status in 19th century England.",
        rating = 4.7,
        reviewsCount = 180,
        likes = 280,
        shares = 90
    ),
    Book(
        id = 5,
        title = "Moby Dick",
        author = "Herman Melville",
        coverImageUrl = "https://i.pinimg.com/736x/a5/eb/bd/a5ebbd187a4263cd2e934318da6c1fbc.jpg", // Will use drawable fallback if empty
        description = "A thrilling adventure story and an exploration of obsession, revenge, and the human struggle with nature, Moby Dick follows the journey of Captain Ahab and his vengeful pursuit of the white whale.",
        rating = 4.2,
        reviewsCount = 130,
        likes = 220,
        shares = 70
    )
)
