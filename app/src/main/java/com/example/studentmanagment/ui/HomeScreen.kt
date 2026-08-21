package com.example.studentmanagment.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Announcement
import com.example.studentmanagment.model.Student
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(student: Student, announcements: List<Announcement> = emptyList()) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Quick Overview",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (student.isDropped) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Column {
                            Text("ATTENTION: BATCH DROPPED", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.error)
                            Text("Your registration has been suspended due to low attendance.", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        item {
            QuickFactsCarousel()
        }
        
        item {
            GPACard(student.gpa)
        }

        item {
            AttendanceCard(student.attendance)
        }

        item {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Announcements", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (announcements.isEmpty()) {
                        Text("No new announcements.", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        announcements.forEach { announcement ->
                            Text("• ${announcement.title}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                            Text(announcement.content, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 12.dp, bottom = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GPACard(gpa: Double) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Current GPA", style = MaterialTheme.typography.titleMedium)
            Text(
                text = gpa.toString(),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun AttendanceCard(percentage: Double) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn()
    ) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Overall Attendance", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (percentage / 100).toFloat() },
                    modifier = Modifier.fillMaxWidth().height(12.dp),
                    color = if (percentage > 75) MaterialTheme.colorScheme.primary else Color.Red,
                )
                Text(
                    text = "${percentage}%",
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun QuickFactsCarousel() {
    val facts = remember {
        listOf(
            "Did you know? The library is open 24/7 during finals week!",
            "Pro-tip: You can get free coffee at the Student Union on Fridays.",
            "Fact: Students who attend 90% of classes have a 20% higher GPA.",
            "Reminder: The campus shuttle runs every 15 minutes.",
            "Quote: 'The beautiful thing about learning is that no one can take it away from you.'"
        )
    }

    var currentFactIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentFactIndex = (currentFactIndex + 1) % facts.size
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Default.Lightbulb,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            
            AnimatedContent(
                targetState = facts[currentFactIndex],
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn()).togetherWith(
                        slideOutVertically { height -> -height } + fadeOut())
                },
                label = "FactAnimation"
            ) { fact ->
                Text(
                    text = fact,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
