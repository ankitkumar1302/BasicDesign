package com.viprab.basicdesign.ui.stats

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viprab.basicdesign.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

data class ReadingGoal(
    val target: Int,
    val current: Int,
    val daysLeft: Int
)

data class GenreStats(
    val genre: String,
    val count: Int,
    val percentage: Float,
    val color: Color
)

@Composable
fun StatsScreen() {
    val scrollState = rememberScrollState()
    
    // Sample data
    val readingGoal = ReadingGoal(target = 50, current = 32, daysLeft = 120)
    val totalPagesRead = 8567
    val averageRating = 4.2f
    val readingStreak = 15
    
    val genreStats = listOf(
        GenreStats("Fiction", 12, 0.3f, Color(0xFF6C63FF)),
        GenreStats("Non-Fiction", 8, 0.2f, Color(0xFF4CAF50)),
        GenreStats("Mystery", 6, 0.15f, Color(0xFF9C27B0)),
        GenreStats("Romance", 5, 0.125f, Color(0xFFE91E63)),
        GenreStats("Sci-Fi", 4, 0.1f, Color(0xFF03A9F4)),
        GenreStats("Others", 5, 0.125f, Color(0xFFFF9800))
    )
    
    val monthlyReadingData = listOf(
        "Jan" to 3,
        "Feb" to 5,
        "Mar" to 4,
        "Apr" to 6,
        "May" to 4,
        "Jun" to 7,
        "Jul" to 5
    )

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
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Reading Statistics",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Yearly Goal Progress
            YearlyGoalCard(readingGoal)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quick Stats Grid
            QuickStatsGrid(
                totalPagesRead = totalPagesRead,
                averageRating = averageRating,
                readingStreak = readingStreak,
                booksCompleted = readingGoal.current
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Genre Distribution
            GenreDistributionCard(genreStats)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Monthly Reading Chart
            MonthlyReadingChart(monthlyReadingData)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Reading Achievements
            ReadingAchievements()
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun YearlyGoalCard(goal: ReadingGoal) {
    val progress = goal.current.toFloat() / goal.target
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "2024 Reading Goal",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Circular Progress
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(200.dp)
            ) {
                CircularProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 16.dp,
                    color = AccentColor,
                    trackColor = Color(0xFF2D2B40)
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${goal.current}",
                        style = MaterialTheme.typography.displayMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "of ${goal.target} books",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = AccentColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${goal.daysLeft}",
                        style = MaterialTheme.typography.titleMedium,
                        color = WarningColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Days Left",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${goal.target - goal.current}",
                        style = MaterialTheme.typography.titleMedium,
                        color = ErrorColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "To Go",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuickStatsGrid(
    totalPagesRead: Int,
    averageRating: Float,
    readingStreak: Int,
    booksCompleted: Int
) {
    val stats = listOf(
        Triple("Pages Read", totalPagesRead.toString(), Icons.Default.MenuBook),
        Triple("Avg Rating", String.format("%.1f", averageRating), Icons.Default.Star),
        Triple("Reading Streak", "$readingStreak days", Icons.Default.LocalFireDepartment),
        Triple("Books Read", booksCompleted.toString(), Icons.Default.CheckCircle)
    )

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stats.forEach { (label, value, icon) ->
            QuickStatCard(
                label = label,
                value = value,
                icon = icon
            )
        }
    }
}

@Composable
private fun QuickStatCard(
    label: String,
    value: String,
    icon: ImageVector
) {
    val infiniteTransition = rememberInfiniteTransition(label = "stat")
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
            .width(170.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AccentColor.copy(alpha = 0.1f),
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd)
                    .scale(scale)
            )
            
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
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
    }
}

@Composable
private fun GenreDistributionCard(genreStats: List<GenreStats>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Genre Distribution",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Pie Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                PieChart(
                    data = genreStats,
                    modifier = Modifier.size(180.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Legend
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                genreStats.forEach { stat ->
                    GenreLegendItem(stat)
                }
            }
        }
    }
}

@Composable
private fun PieChart(
    data: List<GenreStats>,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(
        modifier = modifier.rotate(rotation)
    ) {
        val total = data.sumOf { it.count }.toFloat()
        var startAngle = -90f
        
        data.forEach { stat ->
            val sweepAngle = (stat.count / total) * 360f
            
            drawArc(
                color = stat.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = size
            )
            
            startAngle += sweepAngle
        }
    }
}

@Composable
private fun GenreLegendItem(stat: GenreStats) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(stat.color)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = stat.genre,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = "${stat.count} books",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
    }
}

@Composable
private fun MonthlyReadingChart(data: List<Pair<String, Int>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Monthly Reading Progress",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Bar Chart
            BarChart(
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
private fun BarChart(
    data: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOf { it.second }
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { (month, value) ->
            val animatedHeight by animateFloatAsState(
                targetValue = (value.toFloat() / maxValue),
                animationSpec = tween(1000, easing = FastOutSlowInEasing),
                label = "height"
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(animatedHeight)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(AccentColor, AccentColor.copy(alpha = 0.6f))
                            )
                        )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = month,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ReadingAchievements() {
    val achievements = listOf(
        Achievement("Bookworm", "Read 25 books", Icons.Default.AutoStories, true, SuccessColor),
        Achievement("Speed Reader", "Read 5 books in a month", Icons.Default.Speed, true, AccentColor),
        Achievement("Genre Explorer", "Read from 5 different genres", Icons.Default.Explore, false, WarningColor),
        Achievement("Consistent Reader", "Maintain 30-day streak", Icons.Default.CalendarMonth, false, ErrorColor)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                achievements.forEach { achievement ->
                    AchievementItem(achievement)
                }
            }
        }
    }
}

data class Achievement(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val isUnlocked: Boolean,
    val color: Color
)

@Composable
private fun AchievementItem(achievement: Achievement) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (achievement.isUnlocked) 1f else 0.3f,
        animationSpec = tween(500),
        label = "alpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (achievement.isUnlocked) 
                    achievement.color.copy(alpha = 0.1f) 
                else 
                    Color(0xFF2D2B40)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    if (achievement.isUnlocked)
                        achievement.color.copy(alpha = 0.2f)
                    else
                        Color(0xFF2D2B40)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = achievement.icon,
                contentDescription = null,
                tint = if (achievement.isUnlocked) achievement.color else TextSecondary,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer(alpha = animatedAlpha)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (achievement.isUnlocked) TextPrimary else TextSecondary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = achievement.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
        
        if (achievement.isUnlocked) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = achievement.color,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Lock,
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
fun StatsScreenPreview() {
    BasicDesignTheme {
        StatsScreen()
    }
}