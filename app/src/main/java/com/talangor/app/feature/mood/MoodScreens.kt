package com.talangor.app.feature.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.talangor.app.domain.model.ActionHistoryItem
import com.talangor.app.domain.model.SuggestedAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSelectionScreen(
    state: MoodUiState,
    onMoodSelected: (MoodChoice) -> Unit,
    onHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "تلنگر") },
                actions = {
                    TextButton(onClick = onHistoryClick) {
                        Text(text = "تاریخچه")
                    }
                }
            )
        }
    ) { padding ->
        StepList(
            padding = padding,
            title = "الان چه حالی داری؟",
            subtitle = "یک وضعیت را انتخاب کن تا فقط یک قدم کوچک پیشنهاد شود."
        ) {
            item {
                StatsSummary(history = state.history)
            }

            items(state.moods) { mood ->
                ChoiceCard(
                    title = mood.label,
                    description = mood.description,
                    onClick = { onMoodSelected(mood) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergySelectionScreen(
    state: MoodUiState,
    onEnergySelected: (EnergyChoice) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = state.selectedMood?.label ?: "سطح انرژی") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        StepList(
            padding = padding,
            title = "چقدر انرژی داری؟",
            subtitle = "پیشنهاد با توان همین لحظه‌ات هماهنگ می‌شود."
        ) {
            items(state.energyLevels) { energy ->
                ChoiceCard(
                    title = energy.label,
                    description = energy.description,
                    onClick = { onEnergySelected(energy) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestedActionScreen(
    state: MoodUiState,
    onDoneClick: () -> Unit,
    onNotDoneClick: () -> Unit,
    onAnotherClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "پیشنهاد تلنگر") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    completed: Boolean,
    onHelpedClick: (Boolean) -> Unit,
    onNewMoodClick: () -> Unit,
    onHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "نتیجه") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (completed) {
                Text(
                    text = "این قدم کوچک انجام شد؟ عالی. کمک کرد؟",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "بازخوردت فقط برای بهتر شدن پیشنهادهای بعدی ذخیره می‌شود.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onHelpedClick(true) }
                ) {
                    Text(text = "کمک کرد")
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onHelpedClick(false) }
                ) {
                    Text(text = "کمک نکرد")
                }
            } else {
                Text(
                    text = "اشکالی ندارد؛ همین که وضعیتت را دیدی یک قدم است.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "می‌توانی بعدا با یک انتخاب تازه دوباره شروع کنی.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNewMoodClick
                ) {
                    Text(text = "شروع دوباره")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onHistoryClick
            ) {
                Text(text = "دیدن تاریخچه")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    history: List<ActionHistoryItem>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "تاریخچه تلنگرها") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "هنوز تلنگری ثبت نشده است.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    StatsSummary(history = history)
                }

                items(history) { item ->
                    HistoryItemCard(item = item)
                }
            }
        }
    }
}

@Composable
private fun StepList(
    padding: PaddingValues,
    title: String,
    subtitle: String,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            content()
        }
    )
}

@Composable
private fun ChoiceCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StatsSummary(history: List<ActionHistoryItem>) {
    val completedCount = history.count { it.wasCompleted }
    val helpedCount = history.count { it.wasCompleted && it.helped }
    val skippedCount = history.count { !it.wasCompleted }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "آمار کوچک",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "تا حالا $helpedCount بار یک تلنگر کمک کرده از وضعیت بد فاصله بگیری.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatText(label = "انجام شد", value = completedCount)
                StatText(label = "کمک کرد", value = helpedCount)
                StatText(label = "انجام نشد", value = skippedCount)
            }
        }
    }
}

@Composable
private fun StatText(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = action.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = action.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${action.durationMinutes} دقیقه • ${action.category}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        TimerPanel(
            remainingSeconds = state.timerRemainingSeconds,
            totalSeconds = state.timerTotalSeconds,
            isRunning = state.isTimerRunning
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDoneClick
        ) {
            Text(text = "انجام شد")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNotDoneClick
        ) {
            Text(text = "انجام نشد")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAnotherClick
        ) {
            Text(text = "پیشنهاد دیگر")
        }
    }
}

@Composable
private fun TimerPanel(
    remainingSeconds: Int,
    totalSeconds: Int,
    isRunning: Boolean
) {
    val progress = if (totalSeconds == 0) {
        0f
    } else {
        remainingSeconds.toFloat() / totalSeconds.toFloat()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "تایمر ساده",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatTime(remainingSeconds),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (isRunning) "در حال شمارش" else "زمان تمام شد",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptySuggestionContent(
    message: String,
    onBackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text(text = "تغییر انتخاب")
        }
    }
}

@Composable
private fun HistoryItemCard(item: ActionHistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.actionTitle ?: "پیشنهاد حذف‌شده",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            item.actionDescription?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = "${moodLabel(item.mood)} • ${energyLabel(item.energyLevel)} • ${resultLabel(item)}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
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
