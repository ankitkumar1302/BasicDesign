package com.viprab.basicdesign.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.viprab.basicdesign.R
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.ui.theme.SurfaceColor
import com.viprab.basicdesign.ui.theme.TextPrimary
import com.viprab.basicdesign.ui.theme.TextSecondary

@Composable
fun BookItemCard(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Aligns content horizontally to the center
        ) {
            // Book Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
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
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Center-Aligned Title and Author
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center-aligns the text and author
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "by ${book.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Statistic Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Rating Badge
                StatisticItem(
                    label = "${book.rating} / 5",
                    icon = Icons.Default.Star,
                    backgroundColor = Color(0xFF2D2B40),
                    iconColor = Color(0xFFFFC107)
                )

                // Likes Badge
                StatisticItem(
                    label = "${formatCount(book.likes)} Likes",
                    icon = Icons.Default.Favorite,
                    backgroundColor = Color(0xFF2D2B40),
                    iconColor = Color(0xFF4CAF50)
                )

                // Shares Badge
                StatisticItem(
                    label = "${formatCount(book.shares)} Shares",
                    icon = Icons.Default.Share,
                    backgroundColor = Color(0xFF2D2B40),
                    iconColor = Color(0xFF03A9F4)
                )
            }
        }
    }
}

@Composable
private fun StatisticItem(
    label: String,
    icon: ImageVector,
    backgroundColor: Color = Color(0xFF33334D),
    iconColor: Color,
    textColor: Color = Color.White
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp)) // Fully rounded
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp), // Padding for badge shape
        color = backgroundColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor, // Icon color
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
            )
        }
    }
}

@SuppressLint("DefaultLocale")
private fun formatCount(count: Int): String {
    return when {
        count >= 1000000000 -> String.format("%.1fB", count / 1000000000.0) // Format for billions
        count >= 1000000 -> String.format("%.1fM", count / 1000000.0) // Format for millions
        count >= 1000 -> String.format("%.1fK", count / 1000.0) // Format for thousands
        else -> count.toString() // Small numbers remain unchanged
    }
}

@Composable
@Preview
fun BookItemCardPreview() {
    BookItemCard(
        book = Book(
            id = 5,
            title = "The Great Gatsby",
            author = "F. Scott Fitzgerald",
            coverImageUrl = "https://images.unsplash.com/photo-1612838320302-4b3b3b3b3b3b",
            rating = 4.5,
            reviewsCount = 1234,
            likes = 1234567,
            description = "A classic novel of the Roaring Twenties that explores themes of wealth, love, and the American Dream.",
            shares = 987654321
        ),
        onClick = {}
    )
}
