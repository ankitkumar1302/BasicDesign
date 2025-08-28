package com.viprab.basicdesign.ui.readinglist

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.viprab.basicdesign.R
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import com.viprab.basicdesign.ui.theme.*

data class ReadingBook(
    val book: Book,
    val status: ReadingStatus,
    val progress: Float = 0f,
    val startDate: String? = null,
    val endDate: String? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 300
)

enum class ReadingStatus {
    CURRENTLY_READING,
    WANT_TO_READ,
    COMPLETED,
    ABANDONED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingListScreen(
    onBookClick: (Book) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Currently Reading", "Want to Read", "Completed")
    
    // Sample data
    val readingBooks = remember {
        listOf(
            ReadingBook(sampleBooks[0], ReadingStatus.CURRENTLY_READING, 0.65f, "Jan 15, 2024", currentPage = 195, totalPages = 300),
            ReadingBook(sampleBooks[1], ReadingStatus.CURRENTLY_READING, 0.30f, "Jan 20, 2024", currentPage = 90, totalPages = 300),
            ReadingBook(sampleBooks[2], ReadingStatus.WANT_TO_READ),
            ReadingBook(sampleBooks[3], ReadingStatus.WANT_TO_READ),
            ReadingBook(sampleBooks[4], ReadingStatus.COMPLETED, 1f, "Dec 1, 2023", "Dec 15, 2023", 300, 300),
            ReadingBook(sampleBooks[5], ReadingStatus.COMPLETED, 1f, "Nov 10, 2023", "Nov 25, 2023", 400, 400)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PrimaryDark, PrimaryLight)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with Stats
            ReadingListHeader(readingBooks)
            
            // Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = TextPrimary,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = AccentColor
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
            
            // Content based on selected tab
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    slideInHorizontally { it } with slideOutHorizontally { -it }
                },
                label = "tab_content"
            ) { tab ->
                when (tab) {
                    0 -> CurrentlyReadingList(
                        books = readingBooks.filter { it.status == ReadingStatus.CURRENTLY_READING },
                        onBookClick = onBookClick
                    )
                    1 -> WantToReadList(
                        books = readingBooks.filter { it.status == ReadingStatus.WANT_TO_READ },
                        onBookClick = onBookClick
                    )
                    2 -> CompletedList(
                        books = readingBooks.filter { it.status == ReadingStatus.COMPLETED },
                        onBookClick = onBookClick
                    )
                }
            }
        }
        
        // Floating Action Button
        ExtendedFloatingActionButton(
            onClick = { /* Add new book */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = AccentColor,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Book"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Book")
        }
    }
}

@Composable
private fun ReadingListHeader(readingBooks: List<ReadingBook>) {
    val currentlyReading = readingBooks.count { it.status == ReadingStatus.CURRENTLY_READING }
    val completed = readingBooks.count { it.status == ReadingStatus.COMPLETED }
    val totalBooks = readingBooks.size

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Your Reading Journey",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    value = currentlyReading.toString(),
                    label = "Reading",
                    icon = Icons.Default.AutoStories,
                    color = AccentColor
                )
                StatItem(
                    value = completed.toString(),
                    label = "Completed",
                    icon = Icons.Default.CheckCircle,
                    color = SuccessColor
                )
                StatItem(
                    value = totalBooks.toString(),
                    label = "Total Books",
                    icon = Icons.Default.LibraryBooks,
                    color = WarningColor
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}

@Composable
private fun CurrentlyReadingList(
    books: List<ReadingBook>,
    onBookClick: (Book) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { readingBook ->
            CurrentlyReadingCard(
                readingBook = readingBook,
                onClick = { onBookClick(readingBook.book) }
            )
        }
    }
}

@Composable
private fun CurrentlyReadingCard(
    readingBook: ReadingBook,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "progress")
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = readingBook.progress,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Book Cover
            Card(
                modifier = Modifier.size(width = 80.dp, height = 120.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(readingBook.book.coverImageUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.cover)
                            .error(R.drawable.error)
                            .build()
                    ),
                    contentDescription = readingBook.book.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Book Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = readingBook.book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = readingBook.book.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Progress Info
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = AccentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${readingBook.currentPage} / ${readingBook.totalPages} pages",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Progress Bar
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = AccentColor,
                    trackColor = Color(0xFF2D2B40),
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${(readingBook.progress * 100).toInt()}% Complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentColor,
                        fontWeight = FontWeight.Medium
                    )
                    
                    readingBook.startDate?.let {
                        Text(
                            text = "Started $it",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WantToReadList(
    books: List<ReadingBook>,
    onBookClick: (Book) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { readingBook ->
            WantToReadCard(
                book = readingBook.book,
                onClick = { onBookClick(readingBook.book) }
            )
        }
    }
}

@Composable
private fun WantToReadCard(
    book: Book,
    onClick: () -> Unit
) {
    var isSwipedToRead by remember { mutableStateOf(false) }
    val offset by animateFloatAsState(
        targetValue = if (isSwipedToRead) 100f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offset"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book Cover
            Card(
                modifier = Modifier.size(width = 60.dp, height = 90.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(book.coverImageUrl)
                            .crossfade(true)
                            .placeholder(R.drawable.cover)
                            .error(R.drawable.error)
                            .build()
                    ),
                    contentDescription = book.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Book Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${book.rating} · ${book.reviewsCount} reviews",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            
            // Action Button
            FilledIconButton(
                onClick = { isSwipedToRead = true },
                containerColor = AccentColor.copy(alpha = 0.2f),
                contentColor = AccentColor
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start Reading"
                )
            }
        }
    }
}

@Composable
private fun CompletedList(
    books: List<ReadingBook>,
    onBookClick: (Book) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { readingBook ->
            CompletedBookCard(
                readingBook = readingBook,
                onClick = { onBookClick(readingBook.book) }
            )
        }
    }
}

@Composable
private fun CompletedBookCard(
    readingBook: ReadingBook,
    onClick: () -> Unit
) {
    val rotation by rememberInfiniteTransition(label = "badge").animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Book Cover
                Card(
                    modifier = Modifier.size(width = 60.dp, height = 90.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(readingBook.book.coverImageUrl)
                                .crossfade(true)
                                .placeholder(R.drawable.cover)
                                .error(R.drawable.error)
                                .build()
                        ),
                        contentDescription = readingBook.book.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Book Details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = readingBook.book.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = readingBook.book.author,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = TextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${readingBook.startDate} - ${readingBook.endDate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // User Rating
                    Row {
                        repeat(5) { index ->
                            Icon(
                                imageVector = if (index < 4) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "Your rating",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            
            // Completed Badge
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .rotate(rotation),
                shape = RoundedCornerShape(8.dp),
                color = SuccessColor.copy(alpha = 0.2f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = SuccessColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Completed",
                        style = MaterialTheme.typography.bodySmall,
                        color = SuccessColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ReadingListScreenPreview() {
    BasicDesignTheme {
        ReadingListScreen()
    }
}