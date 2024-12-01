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
    ),
    Book(
        id = 6,
        title = "The Great Gatsby",
        author = "F. Scott Fitzgerald",
        coverImageUrl = "https://i.pinimg.com/736x/94/28/0a/94280a7d3a55588fafadea6657f43131.jpg",
        description = "A classic novel of the Roaring Twenties that explores themes of wealth, love, and the American Dream.",
        rating = 4.6,
        reviewsCount = 1000,
        likes = 1400,
        shares = 350
    ),
    Book(
        id = 7,
        title = "War and Peace",
        author = "Leo Tolstoy",
        coverImageUrl = "https://i.pinimg.com/736x/fc/21/7b/fc217bfd114d9db1e728cf62bd0ccd5b.jpg",
        description = "An epic tale of love, war, and the lives of Russian aristocrats during the Napoleonic era.",
        rating = 4.8,
        reviewsCount = 750,
        likes = 1100,
        shares = 300
    ),
    Book(
        id = 8,
        title = "The Hobbit",
        author = "J.R.R. Tolkien",
        coverImageUrl = "https://i.pinimg.com/564x/3e/93/95/3e93953e9bc9e91dcbdd0949c477ca0b.jpg",
        description = "A fantasy adventure that follows Bilbo Baggins on his quest to reclaim treasure from a dragon.",
        rating = 4.9,
        reviewsCount = 1100,
        likes = 1800,
        shares = 600
    ),
    Book(
        id = 9,
        title = "Brave New World",
        author = "Aldous Huxley",
        coverImageUrl = "https://i.pinimg.com/736x/d5/85/ec/d585ec4cf68d1babf6ba5a7027e8ffd0.jpg",
        description = "A dystopian novel about a society where technology and conditioning control humanity.",
        rating = 4.5,
        reviewsCount = 700,
        likes = 950,
        shares = 240
    ),
    Book(
        id = 10,
        title = "Jane Eyre",
        author = "Charlotte Brontë",
        coverImageUrl = "https://i.pinimg.com/736x/3f/3c/03/3f3c03840021f8d45be86614d58fc645.jpg",
        description = "A gothic romance about an orphaned governess and her mysterious employer.",
        rating = 4.7,
        reviewsCount = 850,
        likes = 1250,
        shares = 400
    ),
    Book(
        id = 11,
        title = "Crime and Punishment",
        author = "Fyodor Dostoevsky",
        coverImageUrl = "https://i.pinimg.com/736x/70/da/a2/70daa2ba9b0be3cb5c2fc47f1f409f8a.jpg",
        description = "A psychological novel exploring guilt, redemption, and morality through the story of a young man who commits a crime.",
        rating = 4.6,
        reviewsCount = 820,
        likes = 1100,
        shares = 370
    ),
    Book(
        id = 12,
        title = "The Lord of the Rings",
        author = "J.R.R. Tolkien",
        coverImageUrl = "https://i.pinimg.com/736x/03/a3/ad/03a3ad366e04efa5997cadf813c6c7b4.jpg",
        description = "An epic fantasy adventure about the quest to destroy the One Ring and save Middle-earth.",
        rating = 4.9,
        reviewsCount = 2000,
        likes = 3000,
        shares = 800
    ),
    Book(
        id = 13,
        title = "Frankenstein",
        author = "Mary Shelley",
        coverImageUrl = "https://i.pinimg.com/736x/ce/f8/4a/cef84a3c5fc21b3386f47c4d90cc1fdb.jpg",
        description = "A gothic horror tale about a scientist who creates a living being, with tragic consequences.",
        rating = 4.4,
        reviewsCount = 500,
        likes = 850,
        shares = 210
    ),
    Book(
        id = 14,
        title = "The Odyssey",
        author = "Homer",
        coverImageUrl = "https://i.pinimg.com/736x/a2/ca/b9/a2cab9763c44c99b169282b5146b76cb.jpg",
        description = "An ancient Greek epic poem about the journey of Odysseus returning home after the Trojan War.",
        rating = 4.7,
        reviewsCount = 650,
        likes = 900,
        shares = 290
    ),
    Book(
        id = 15,
        title = "The Divine Comedy",
        author = "Dante Alighieri",
        coverImageUrl = "https://i.pinimg.com/736x/78/c1/c5/78c1c592187da5216560a126afae2255.jpg",
        description = "An allegorical journey through Hell, Purgatory, and Heaven, exploring themes of sin, redemption, and divine justice.",
        rating = 4.8,
        reviewsCount = 1200,
        likes = 2000,
        shares = 600
    )

)
