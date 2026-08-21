package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.ScheduleItem

@Composable
fun ScheduleScreen(schedule: List<ScheduleItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Weekly Schedule", style = MaterialTheme.typography.headlineMedium)
        }

        items(schedule) { item ->
            ScheduleCard(item)
        }
    }
}

@Composable
fun ScheduleCard(item: ScheduleItem) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.width(80.dp)) {
                Text(item.day, style = MaterialTheme.typography.titleSmall)
                Text(item.time, style = MaterialTheme.typography.bodySmall)
            }
            VerticalDivider(modifier = Modifier.height(40.dp))
            Column {
                Text(item.subject, style = MaterialTheme.typography.titleMedium)
                Text("Room: ${item.room}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
