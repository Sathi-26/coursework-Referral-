package com.example.studentmanagment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Student

@Composable
fun AdminAnalyticsScreen(students: List<Student>) {
    val avgAttendance = if (students.isNotEmpty()) students.map { it.attendance }.average() else 0.0
    val avgGPA = if (students.isNotEmpty()) students.map { it.gpa }.average() else 0.0
    val dropOutRate = if (students.isNotEmpty()) (students.count { it.isDropped }.toDouble() / students.size) * 100 else 0.0

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        item {
            Text("Analytics & Insights", style = MaterialTheme.typography.headlineMedium)
        }

        item {
            AnalyticsBarChart("Average Attendance", "${avgAttendance.toInt()}%", avgAttendance.toFloat() / 100)
        }

        item {
            AnalyticsBarChart("Average GPA", String.format("%.2f", avgGPA), (avgGPA.toFloat() / 4.0f))
        }

        item {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Enrollment Health", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total Active: ${students.count { !it.isDropped }}", style = MaterialTheme.typography.bodyLarge)
                    Text("Total Dropped: ${students.count { it.isDropped }}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.error)
                    Text("Retention Rate: ${100 - dropOutRate.toInt()}%", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun AnalyticsBarChart(label: String, value: String, progress: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.labelLarge)
            Text(value, style = MaterialTheme.typography.titleSmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(16.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeCap = StrokeCap.Round
        )
    }
}
