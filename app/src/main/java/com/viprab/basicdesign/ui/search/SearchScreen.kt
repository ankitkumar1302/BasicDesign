package com.viprab.basicdesign.ui.search

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viprab.basicdesign.components.BookItemCard
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks
import com.viprab.basicdesign.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onBookClick: (Book) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var searchResults by remember { mutableStateOf<List<Book>>(emptyList()) }
    var recentSearches by remember { mutableStateOf(listOf("Fantasy", "Science Fiction", "Romance", "Mystery")) }
    var popularGenres by remember { mutableStateOf(listOf("Fiction", "Non-Fiction", "Biography", "Self-Help", "Horror", "Thriller")) }
    
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Animated Search Bar
            AnimatedSearchBar(
                query = searchQuery,
                onQueryChange = { 
                    searchQuery = it
                    isSearching = it.isNotEmpty()
                    searchResults = if (it.isNotEmpty()) {
                        sampleBooks.filter { book ->
                            book.title.contains(it, ignoreCase = true) ||
                            book.author.contains(it, ignoreCase = true)
                        }
                    } else {
                        emptyList()
                    }
                },
                onSearch = {
                    keyboardController?.hide()
                    if (searchQuery.isNotEmpty() && !recentSearches.contains(searchQuery)) {
                        recentSearches = listOf(searchQuery) + recentSearches.take(3)
                    }
                },
                onClear = {
                    searchQuery = ""
                    isSearching = false
                    searchResults = emptyList()
                },
                focusRequester = focusRequester
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Content based on search state
            AnimatedContent(
                targetState = isSearching,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
                },
                label = "search_content"
            ) { searching ->
                if (searching) {
                    // Search Results
                    SearchResults(
                        results = searchResults,
                        onBookClick = onBookClick
                    )
                } else {
                    // Default Content (Recent & Popular)
                    Column {
                        // Recent Searches
                        if (recentSearches.isNotEmpty()) {
                            Text(
                                text = "Recent Searches",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                recentSearches.forEach { search ->
                                    RecentSearchChip(
                                        text = search,
                                        onClick = {
                                            searchQuery = search
                                            isSearching = true
                                            searchResults = sampleBooks.filter { book ->
                                                book.title.contains(search, ignoreCase = true) ||
                                                book.author.contains(search, ignoreCase = true)
                                            }
                                        }
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                        }

                        // Popular Genres
                        Text(
                            text = "Popular Genres",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        GenreGrid(
                            genres = popularGenres,
                            onGenreClick = { genre ->
                                searchQuery = genre
                                isSearching = true
                                searchResults = sampleBooks.filter { book ->
                                    // In a real app, books would have genre field
                                    book.description.contains(genre, ignoreCase = true)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Trending Searches
                        Text(
                            text = "Trending Now",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        TrendingList()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    focusRequester: FocusRequester
) {
    val animatedElevation by animateDpAsState(
        targetValue = if (query.isNotEmpty()) 12.dp else 8.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(animatedElevation)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = {
                Text(
                    text = "Search books, authors, genres...",
                    color = TextSecondary
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = if (query.isNotEmpty()) AccentColor else TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.isNotEmpty(),
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    IconButton(onClick = onClear) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextSecondary
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch() }
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AccentColor,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            )
        )
    }
}

@Composable
private fun SearchResults(
    results: List<Book>,
    onBookClick: (Book) -> Unit
) {
    if (results.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary
                )
                Text(
                    text = "Try searching with different keywords",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary.copy(alpha = 0.7f)
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "${results.size} results found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(results) { book ->
                BookItemCard(
                    book = book,
                    onClick = { onBookClick(book) }
                )
            }
        }
    }
}

@Composable
private fun RecentSearchChip(
    text: String,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )

    Surface(
        modifier = Modifier.clip(CircleShape),
        color = SurfaceColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = AccentColor.copy(alpha = shimmerAlpha),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenreGrid(
    genres: List<String>,
    onGenreClick: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        genres.forEach { genre ->
            GenreCard(
                genre = genre,
                onClick = { onGenreClick(genre) }
            )
        }
    }
}

@Composable
private fun GenreCard(
    genre: String,
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

    Card(
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .size(width = 160.dp, height = 100.dp)
            .clickable {
                isPressed = true
                onClick()
                isPressed = false
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (genre) {
                "Fiction" -> Color(0xFF6C63FF)
                "Non-Fiction" -> Color(0xFF4CAF50)
                "Biography" -> Color(0xFFFF9800)
                "Self-Help" -> Color(0xFF03A9F4)
                "Horror" -> Color(0xFFE91E63)
                "Thriller" -> Color(0xFF9C27B0)
                else -> AccentColor
            }.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = genre,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TrendingList() {
    val trendingItems = listOf(
        "Best Sellers 2024" to Icons.Default.TrendingUp,
        "Award Winners" to Icons.Default.EmojiEvents,
        "New Releases" to Icons.Default.NewReleases,
        "Editor's Choice" to Icons.Default.Star
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        trendingItems.forEach { (title, icon) ->
            TrendingItem(
                title = title,
                icon = icon,
                onClick = { /* Handle trending click */ }
            )
        }
    }
}

@Composable
private fun TrendingItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AccentColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
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
fun SearchScreenPreview() {
    BasicDesignTheme {
        SearchScreen()
    }
}