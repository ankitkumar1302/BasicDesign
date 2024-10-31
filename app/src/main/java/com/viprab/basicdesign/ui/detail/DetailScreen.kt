package com.viprab.basicdesign.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.viprab.basicdesign.R
import com.viprab.basicdesign.model.Book
import com.viprab.basicdesign.model.sampleBooks

@Composable
fun DetailScreen(navController: NavController, bookId: Int) {
    val viewModel: DetailScreenViewModel = viewModel()
    LaunchedEffect(bookId) { viewModel.loadBook(bookId) }
    val book = viewModel.book.collectAsState().value
    var showImagePopup by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E1E2C), Color(0xFF252534))
                )
            )
            .padding(16.dp)
    ) {
        book?.let {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                // Book Cover Image
                val painter = rememberAsyncImagePainter(
                    model = it.coverImageUrl.ifEmpty { R.drawable.cover },
                    placeholder = painterResource(R.drawable.c256cc070d807f343ce7f0af9f273c1e),
                    error = painterResource(R.drawable.error)
                )

                Image(
                    painter = painter,
                    contentDescription = it.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showImagePopup = true }
                )

                Spacer(modifier = Modifier.height(16.dp))
                BookDetailsCard(book = it)
            }
        } ?: run {
            Text(
                "Book not found",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (showImagePopup) {
            ImagePopup(
                imagePainter = rememberAsyncImagePainter(
                    model = book?.coverImageUrl?.ifEmpty { R.drawable.cover }
                ),
                onDismiss = { showImagePopup = false }
            )
        }
    }
}

@Composable
fun BookDetailsCard(book: Book) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C3E)),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = book.description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 22.sp,
                    color = Color(0xFFE0E0E0)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MetricItem(
                    icon = Icons.Default.Star,
                    label = "${book.rating} / 5",
                    tint = Color(0xFFFFC107)
                )
                MetricItem(
                    icon = Icons.Default.ThumbUp,
                    label = "${book.likes} Likes",
                    tint = Color(0xFF4CAF50)
                )
                MetricItem(
                    icon = Icons.Default.Share,
                    label = "${book.shares} Shares",
                    tint = Color(0xFF03DAC5)
                )
            }
        }
    }
}

@Composable
fun MetricItem(icon: ImageVector, label: String, tint: Color) {
    Card(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF383850)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun ImagePopup(
    imagePainter: Painter,
    onDismiss: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(0.7f) }
    var alpha by remember { mutableFloatStateOf(0f) }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(
            durationMillis = 500,
            easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1.0f)
        ), label = ""
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 400), label = ""
    )

    LaunchedEffect(Unit) {
        scale = 1f
        alpha = 1f
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xAA000000)) // Dim background for focus effect
            .clickable { onDismiss() }
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = tween(300)),
            exit = fadeOut(tween(200)) + scaleOut(targetScale = 0.8f, animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Enlarged Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .scale(animatedScale)
                        .alpha(animatedAlpha)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Transparent)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    val sampleBookId = sampleBooks.first().id
    val navController = rememberNavController()
    DetailScreen(navController = navController, bookId = sampleBookId)
}
