package com.talangor.app.feature.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talangor.app.R
import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.model.SuggestedAction

private val DeepInk = Color(0xFF1B114F)
private val Purple = Color(0xFF7D5AE8)
private val PurpleDark = Color(0xFF5B3BC0)
private val PurpleSoft = Color(0xFFE9DDFF)
private val Muted = Color(0xFF6F6688)
private val Glass = Color(0x8FFFFFFF)
private val GlassStrong = Color(0xCFFFFFFF)
private val Border = Color(0xE6FFFFFF)
private val PageGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFFFCFF), Color(0xFFF2EAFF), Color(0xFFE9DEFF))
)

@Composable
fun MoodSelectionScreen(
    state: MoodUiState,
    onMoodSelected: (MoodChoice) -> Unit,
    onHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        modifier = modifier,
        useImageBackground = true,
        bottomBar = { ImageBottomBar() }
    ) {
        item {
            ImageAssetBlock(
                drawableRes = R.drawable.home_title,
                modifier = Modifier.fillMaxWidth(),
                aspectRatio = 2.45f,
                contentDescription = "عنوان صفحه انتخاب وضعیت"
            )
        }

        item {
            HomeStatsAssetPanel(history = state.history)
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                items(state.moods) { mood ->
                    MoodAssetCard(
                        mood = mood,
                        onClick = { onMoodSelected(mood) }
                    )
                }
            }
        }

        item {
            ImageAssetBlock(
                drawableRes = R.drawable.history_panel,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onHistoryClick),
                aspectRatio = 3.45f,
                contentDescription = "تاریخچه"
            )
        }
    }
}

@Composable
fun EnergySelectionScreen(
    state: MoodUiState,
    onEnergySelected: (EnergyChoice) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(modifier = modifier) {
        item {
            HeaderLogo(compact = true)
            StepHeader(
                title = "چقدر انرژی داری؟",
                subtitle = "پیشنهاد با توان همین لحظه‌ات هماهنگ می‌شود."
            )
        }
        items(state.energyLevels) { energy ->
            LargeChoiceCard(
                title = energy.label,
                description = energy.description,
                icon = energyIcon(energy.key),
                onClick = { onEnergySelected(energy) }
            )
        }
        item {
            SecondaryActionButton(text = "بازگشت", onClick = onBackClick)
        }
    }
}

@Composable
fun SuggestedActionScreen(
    state: MoodUiState,
    onDoneClick: () -> Unit,
    onNotDoneClick: () -> Unit,
    onAnotherClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(modifier = modifier) {
        item {
            HeaderLogo(compact = true)
            StepHeader(
                title = "تلنگر پیشنهادی",
                subtitle = "فقط همین قدم کوچک را امتحان کن؛ کافی است."
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator(color = Purple)
                    state.suggestedAction != null -> SuggestedActionContent(
                        state = state,
                        suggestion = state.suggestedAction,
                        onDoneClick = onDoneClick,
                        onNotDoneClick = onNotDoneClick,
                        onAnotherClick = onAnotherClick
                    )
                    else -> EmptySuggestionContent(
                        message = state.errorMessage ?: "پیشنهادی پیدا نشد.",
                        onBackClick = onBackClick
                    )
                }
            }
        }
    }
}

@Composable
fun ResultScreen(
    completed: Boolean,
    onHelpedClick: (Boolean) -> Unit,
    onNewMoodClick: () -> Unit,
    onHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(modifier = modifier) {
        item {
            HeaderLogo(compact = true)
            GlassPanel {
                if (completed) {
                    StepHeader(
                        title = "این قدم کمک کرد؟",
                        subtitle = "بازخوردت فقط برای بهتر شدن پیشنهادهای بعدی ذخیره می‌شود."
                    )
                    PrimaryActionButton(text = "کمک کرد", onClick = { onHelpedClick(true) })
                    Spacer(modifier = Modifier.height(10.dp))
                    SecondaryActionButton(text = "کمک نکرد", onClick = { onHelpedClick(false) })
                } else {
                    StepHeader(
                        title = "اشکالی ندارد",
                        subtitle = "همین که وضعیتت را دیدی یک قدم کوچک است."
                    )
                    PrimaryActionButton(text = "شروع دوباره", onClick = onNewMoodClick)
                }
                Spacer(modifier = Modifier.height(10.dp))
                SecondaryActionButton(text = "دیدن تاریخچه", onClick = onHistoryClick)
            }
        }
    }
}

@Composable
fun HistoryScreen(
    history: List<ActionHistoryItem>,
    onHomeClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenShell(
        modifier = modifier,
        bottomBar = { ImageBottomBar(onHomeClick = onHomeClick) }
    ) {
        item {
            HeaderLogo(compact = true)
            StepHeader(
                title = "تاریخچه تلنگرها",
                subtitle = "مرور وضعیت‌ها و قدم‌های قبلی"
            )
        }
        item {
            StatsSummary(history = history)
        }
        if (history.isEmpty()) {
            item {
                GlassPanel {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "هنوز تلنگری ثبت نشده است.",
                        color = Muted,
                        textAlign = TextAlign.End
                    )
                }
            }
        } else {
            items(history) { item ->
                HistoryItemCard(item = item)
            }
        }
        item {
            SecondaryActionButton(text = "بازگشت", onClick = onBackClick)
        }
    }
}

@Composable
private fun ScreenShell(
    modifier: Modifier = Modifier,
    useImageBackground: Boolean = false,
    bottomBar: (@Composable () -> Unit)? = null,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PageGradient)
    ) {
        if (useImageBackground) {
            Image(
                painter = painterResource(id = R.drawable.bg_main),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = if (useImageBackground) 18.dp else 22.dp,
                top = if (useImageBackground) 18.dp else 22.dp,
                end = if (useImageBackground) 18.dp else 22.dp,
                bottom = if (bottomBar == null) 28.dp else 88.dp
            ),
            verticalArrangement = Arrangement.spacedBy(if (useImageBackground) 2.dp else 10.dp),
            content = content
        )
        if (bottomBar != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                bottomBar()
            }
        }
    }
}

@Composable
private fun HeaderLogo(compact: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.talangor_logo),
            contentDescription = "لوگوی تلنگر",
            modifier = Modifier
                .width(if (compact) 138.dp else 178.dp)
                .height(if (compact) 48.dp else 62.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun ImageAssetBlock(
    drawableRes: Int,
    modifier: Modifier = Modifier,
    aspectRatio: Float,
    contentDescription: String? = null
) {
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = contentDescription,
        modifier = modifier.aspectRatio(aspectRatio),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun MoodAssetCard(mood: MoodChoice, onClick: () -> Unit) {
    ImageAssetBlock(
        drawableRes = moodCardDrawable(mood.key),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        aspectRatio = 1.76f,
        contentDescription = mood.label
    )
}

@Composable
private fun HomeStatsAssetPanel(history: List<ActionHistoryItem>) {
    val completedCount = history.count { it.wasCompleted }
    val helpedCount = history.count { it.wasCompleted && it.helped }
    val skippedCount = history.count { !it.wasCompleted }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.95f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.stats_panel),
            contentDescription = "آمار کوچک",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 70.dp, start = 70.dp),
            text = "آمار",
            color = DeepInk,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Right
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(start = 42.dp, end = 42.dp, bottom = 40.dp),
            text = "تا حالا $helpedCount بار یک تلنگر کمک کرده از وضعیت بد فاصله بگیری",
            color = Muted,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Right
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 48.dp, end = 48.dp, bottom = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            HomeStatCounter(label = "", value = skippedCount)
            HomeStatCounter(label = "", value = helpedCount)
            HomeStatCounter(label = "", value = completedCount)
        }
    }
}

@Composable
private fun HomeStatCounter(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            color = DeepInk,
            fontSize = 20.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(text = label, color = Muted, fontSize = 10.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun ImageBottomBar(onHomeClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bottom_nav),
            contentDescription = "ناوبری پایین",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f).fillMaxHeight())
            Box(modifier = Modifier.weight(1f).fillMaxHeight())
            Box(modifier = Modifier.weight(1f).fillMaxHeight())
            Box(modifier = Modifier.weight(1f).fillMaxHeight())
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(onClick = onHomeClick)
            )
        }
    }
}

@Composable
private fun StepHeader(title: String, subtitle: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
                text = title,
                color = DeepInk,
                fontSize = 29.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Right
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
                text = subtitle,
                color = Muted,
                fontSize = 16.sp,
                textAlign = TextAlign.Right,
                lineHeight = 27.sp
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun GlassPanel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(24.dp, RoundedCornerShape(28.dp), ambientColor = Color(0x55BFA9FF), spotColor = Color(0x55BFA9FF))
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xBFFFFFFF), Glass, Color(0x66F4ECFF))
                )
            )
            .border(1.4.dp, Border, RoundedCornerShape(28.dp))
            .padding(17.dp),
        content = content
    )
}

@Composable
private fun StatsSummary(history: List<ActionHistoryItem>) {
    val completedCount = history.count { it.wasCompleted }
    val helpedCount = history.count { it.wasCompleted && it.helped }
    val skippedCount = history.count { !it.wasCompleted }

    GlassPanel {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "آمار کوچک",
                color = DeepInk,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            RoundIcon(size = 54.dp) { StatsIcon() }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                text = "تا حالا $helpedCount بار یک تلنگر کمک کرده از وضعیت بد فاصله بگیری.",
                color = Muted,
                fontSize = 15.sp,
                lineHeight = 24.sp
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatText(label = "انجام شد", value = completedCount) { StarIcon() }
            StatDivider()
            StatText(label = "کمک کرد", value = helpedCount) { CheckIcon() }
            StatDivider()
            StatText(label = "انجام نشده", value = skippedCount) { CloseIcon() }
        }
    }
}

@Composable
private fun StatText(label: String, value: Int, icon: @Composable () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            color = DeepInk,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(text = label, color = Muted, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(8.dp))
        RoundIcon(size = 38.dp, content = icon)
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .height(86.dp)
            .width(1.dp)
            .background(PurpleSoft)
    )
}

@Composable
private fun LargeChoiceCard(
    title: String,
    description: String,
    icon: String,
    onClick: () -> Unit
) {
    GlassPanel(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RoundIcon(size = 62.dp, filled = true) {
                EnergyCanvasIcon(icon)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    color = DeepInk,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    color = Muted,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}

@Composable
private fun SuggestedActionContent(
    state: MoodUiState,
    suggestion: SuggestedAction,
    onDoneClick: () -> Unit,
    onNotDoneClick: () -> Unit,
    onAnotherClick: () -> Unit
) {
    val action = suggestion.action

    GlassPanel {
        Text(text = action.title, color = DeepInk, fontSize = 26.sp, lineHeight = 34.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = action.description, color = Muted, style = MaterialTheme.typography.bodyLarge, lineHeight = 28.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "${action.durationMinutes} دقیقه • ${action.category}", color = PurpleDark, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))
        TimerPanel(
            remainingSeconds = state.timerRemainingSeconds,
            totalSeconds = state.timerTotalSeconds,
            isRunning = state.isTimerRunning
        )
        Spacer(modifier = Modifier.height(18.dp))
        PrimaryActionButton(text = "انجام شد", onClick = onDoneClick)
        Spacer(modifier = Modifier.height(10.dp))
        SecondaryActionButton(text = "انجام نشد", onClick = onNotDoneClick)
        Spacer(modifier = Modifier.height(10.dp))
        SecondaryActionButton(text = "پیشنهاد دیگر", onClick = onAnotherClick)
    }
}

@Composable
private fun TimerPanel(remainingSeconds: Int, totalSeconds: Int, isRunning: Boolean) {
    val progress = if (totalSeconds == 0) 0f else remainingSeconds.toFloat() / totalSeconds.toFloat()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(GlassStrong)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "تایمر ساده", color = DeepInk, fontWeight = FontWeight.Bold)
            Text(text = formatTime(remainingSeconds), color = DeepInk, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = Purple,
            trackColor = PurpleSoft,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = if (isRunning) "در حال شمارش" else "زمان تمام شد", color = Muted, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun EmptySuggestionContent(message: String, onBackClick: () -> Unit) {
    GlassPanel {
        Text(modifier = Modifier.fillMaxWidth(), text = message, color = Muted, textAlign = TextAlign.End)
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryActionButton(text = "تغییر انتخاب", onClick = onBackClick)
    }
}

@Composable
private fun HistoryItemCard(item: ActionHistoryItem) {
    GlassPanel {
        Text(text = item.actionTitle ?: "پیشنهاد حذف‌شده", color = DeepInk, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        item.actionDescription?.let { description ->
            Text(text = description, color = Muted, style = MaterialTheme.typography.bodyMedium, lineHeight = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(text = "${moodLabel(item.mood)} • ${energyLabel(item.energyLevel)} • ${resultLabel(item)}", color = PurpleDark, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun PrimaryActionButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth().height(52.dp),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Purple, contentColor = Color.White)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SecondaryActionButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth().height(52.dp),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, PurpleSoft),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = PurpleDark)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RoundIcon(
    size: androidx.compose.ui.unit.Dp,
    filled: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .shadow(12.dp, CircleShape, ambientColor = PurpleSoft, spotColor = PurpleSoft)
            .clip(CircleShape)
            .background(if (filled) Brush.radialGradient(listOf(Purple, PurpleDark)) else Brush.radialGradient(listOf(Color.White, PurpleSoft)))
            .border(1.dp, Border, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun StatsIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(22.dp)) {
        val bar = PurpleDark
        val width = size.width * .14f
        drawRoundRect(bar, topLeft = Offset(size.width * .2f, size.height * .48f), size = Size(width, size.height * .34f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(5f, 5f))
        drawRoundRect(bar, topLeft = Offset(size.width * .43f, size.height * .26f), size = Size(width, size.height * .56f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(5f, 5f))
        drawRoundRect(bar, topLeft = Offset(size.width * .66f, size.height * .12f), size = Size(width, size.height * .7f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(5f, 5f))
    }
}

@Composable
private fun CheckIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(20.dp)) {
        drawLine(PurpleDark, Offset(size.width * .22f, size.height * .55f), Offset(size.width * .43f, size.height * .75f), strokeWidth = 3.8f, cap = StrokeCap.Round)
        drawLine(PurpleDark, Offset(size.width * .43f, size.height * .75f), Offset(size.width * .78f, size.height * .25f), strokeWidth = 3.8f, cap = StrokeCap.Round)
    }
}

@Composable
private fun CloseIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(18.dp)) {
        drawLine(PurpleDark, Offset(size.width * .25f, size.height * .25f), Offset(size.width * .75f, size.height * .75f), strokeWidth = 3.5f, cap = StrokeCap.Round)
        drawLine(PurpleDark, Offset(size.width * .75f, size.height * .25f), Offset(size.width * .25f, size.height * .75f), strokeWidth = 3.5f, cap = StrokeCap.Round)
    }
}

@Composable
private fun StarIcon() {
    Text(text = "☆", color = PurpleDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun EnergyCanvasIcon(icon: String) {
    Text(text = icon, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

private fun moodCardDrawable(mood: String): Int {
    return when (mood) {
        "anxious" -> R.drawable.mood_anxious
        "bored" -> R.drawable.mood_bored
        "tired" -> R.drawable.mood_tired
        "unfocused" -> R.drawable.mood_unfocused
        "unmotivated" -> R.drawable.mood_unmotivated
        else -> R.drawable.mood_unmotivated
    }
}

private fun energyIcon(energy: String): String {
    return when (energy) {
        "low" -> "۱"
        "medium" -> "۲"
        "high" -> "۳"
        else -> "•"
    }
}

private fun moodLabel(mood: String): String {
    return when (mood) {
        "anxious" -> "مضطرب"
        "bored" -> "بی‌حوصله"
        "tired" -> "خسته"
        "unfocused" -> "بی‌تمرکز"
        "unmotivated" -> "بی‌انگیزه"
        else -> mood
    }
}

private fun energyLabel(energyLevel: String): String {
    return when (energyLevel) {
        "low" -> "انرژی کم"
        "medium" -> "انرژی متوسط"
        "high" -> "انرژی زیاد"
        else -> energyLevel
    }
}

private fun resultLabel(item: ActionHistoryItem): String {
    return when {
        item.wasCompleted && item.helped -> "کمک کرد"
        item.wasCompleted -> "کمک نکرد"
        else -> "انجام نشد"
    }
}
