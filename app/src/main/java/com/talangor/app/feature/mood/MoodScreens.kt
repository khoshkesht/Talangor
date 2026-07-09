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
            subtitle = "یک وضعیت را انتخاب کن تا قدم بعدی را پیشنهاد بدهیم."
        ) {
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
            subtitle = "پیشنهادها بر اساس توان همین لحظه انتخاب می‌شوند."
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
    onCompleteClick: (Boolean) -> Unit,
    onSkipClick: () -> Unit,
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
                    suggestion = state.suggestedAction,
                    onCompleteClick = onCompleteClick,
                    onSkipClick = onSkipClick,
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
    helped: Boolean,
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
            Text(
                text = if (helped) "خوبه؛ این تلنگر به کارت آمد." else "ثبت شد؛ همه پیشنهادها قرار نیست همیشه جواب بدهند.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "بازخوردت کمک می‌کند پیشنهادهای بعدی دقیق‌تر شوند.",
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
private fun SuggestedActionContent(
    suggestion: SuggestedAction,
    onCompleteClick: (Boolean) -> Unit,
    onSkipClick: () -> Unit,
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
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onCompleteClick(true) }
        ) {
            Text(text = "انجام شد و کمک کرد")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onCompleteClick(false) }
        ) {
            Text(text = "انجام شد ولی کمک نکرد")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onSkipClick
            ) {
                Text(text = "رد کردن")
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onAnotherClick
            ) {
                Text(text = "پیشنهاد دیگر")
            }
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

private fun moodLabel(mood: String): String {
    return when (mood) {
        "anxious" -> "مضطرب"
        "bored" -> "بی‌حوصله"
        "tired" -> "خسته"
        "unfocused" -> "حواس‌پرت"
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
        else -> "رد شد"
    }
}
