package com.viprab.basicdesign.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.viprab.basicdesign.R
import com.viprab.basicdesign.model.Book

@Composable
fun BookItemCard(
    book: Book,
    modifier: Modifier = Modifier, // Added Modifier parameter for customization
    onClick: () -> Unit // Callback for click events
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }, // Use onClick for handling clicks
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C3E)),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = if (book.coverImageUrl.isEmpty()) {
                painterResource(id = R.drawable.cover)
            } else {
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(book.coverImageUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.c256cc070d807f343ce7f0af9f273c1e)
                        .error(R.drawable.error)
                        .build()
                )
            }

            Image(
                painter = painter,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                maxLines = 1,
                color = Color.White
            )

            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray,
                maxLines = 1
            )
        }
    }
}
