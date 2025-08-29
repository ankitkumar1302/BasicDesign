package com.viprab.basicdesign.ui.categories

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.viprab.basicdesign.R
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import com.viprab.basicdesign.ui.theme.*

data class Category(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val bookCount: Int,
    val description: String
)

@Composable
fun CategoriesScreen(
    onBookClick: (Book) -> Unit = {},
    onCategoryClick: (Category) -> Unit = {}
) {
    val categories = remember {
        listOf(
            Category(1, "Fiction", Icons.Default.AutoStories, Color(0xFF6C63FF), 156, "Imaginative narratives and stories"),
            Category(2, "Non-Fiction", Icons.Default.MenuBook, Color(0xFF4CAF50), 89, "Factual and informative content"),
            Category(3, "Science Fiction", Icons.Default.RocketLaunch, Color(0xFF03A9F4), 67, "Futuristic and technological tales"),
            Category(4, "Romance", Icons.Default.Favorite, Color(0xFFE91E63), 123, "Love stories and relationships"),
            Category(5, "Mystery", Icons.Default.Search, Color(0xFF9C27B0), 92, "Puzzling and suspenseful stories"),
            Category(6, "Horror", Icons.Default.NightsStay, Color(0xFFFF5722), 45, "Scary and thrilling narratives"),
            Category(7, "Biography", Icons.Default.Person, Color(0xFFFF9800), 78, "Life stories of real people"),
            Category(8, "Self-Help", Icons.Default.EmojiObjects, Color(0xFFFFC107), 104, "Personal development guides"),
            Category(9, "History", Icons.Default.History, Color(0xFF795548), 63, "Past events and civilizations"),
            Category(10, "Poetry", Icons.Default.Edit, Color(0xFF607D8B), 41, "Rhythmic and expressive writing")
        )
    }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PrimaryDark, PrimaryLight)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Header Section
            item {
                Column {
                    Text(
                        text = "Browse Categories",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Discover books by genre",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Featured Categories - Horizontal Scroll
            item {
                Column {
                    Text(
                        text = "Popular Categories",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(categories.take(5)) { category ->
                            FeaturedCategoryCard(
                                category = category,
                                onClick = { 
                                    selectedCategory = category
                                    onCategoryClick(category)
                                }
                            )
                        }
                    }
                }
            }

            // All Categories Grid
            item {
                Text(
                    text = "All Categories",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Category Grid
            item {
                CategoryGrid(
                    categories = categories,
                    onCategoryClick = { category ->
                        selectedCategory = category
                        onCategoryClick(category)
                    }
                )
            }

            // Trending in Categories
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Trending in Fiction",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        TextButton(onClick = { /* View all */ }) {
                            Text("View All", color = AccentColor)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(sampleBooks.take(5)) { book ->
                            TrendingBookCard(
                                book = book,
                                onClick = { onBookClick(book) }
                            )
                        }
                    }
                }
            }

            // New Releases by Category
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "New in Mystery",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        TextButton(onClick = { /* View all */ }) {
                            Text("View All", color = AccentColor)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(sampleBooks.drop(5).take(5)) { book ->
                            TrendingBookCard(
                                book = book,
                                onClick = { onBookClick(book) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeaturedCategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = category.color.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            category.color.copy(alpha = 0.3f),
                            category.color.copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(category.color.copy(alpha = 0.3f))
                            .scale(scale),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = null,
                            tint = category.color,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${category.bookCount} books",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
                
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryGrid(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            CategoryChip(
                category = category,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Surface(
        modifier = Modifier
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            },
        shape = RoundedCornerShape(16.dp),
        color = SurfaceColor,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(category.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    tint = category.color,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${category.bookCount} books",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun TrendingBookCard(
    book: Book,
    onClick: () -> Unit
) {
    val rotation by rememberInfiniteTransition(label = "rotation").animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .width(140.dp)
            .height(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Book Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
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
                
                // Rating Badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .rotate(rotation),
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.7f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = book.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
            }
            
            // Book Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable androidx.compose.foundation.layout.FlowRowScope.() -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        content = content
    )
}

@Preview
@Composable
fun CategoriesScreenPreview() {
    BasicDesignTheme {
        CategoriesScreen()
    }
}