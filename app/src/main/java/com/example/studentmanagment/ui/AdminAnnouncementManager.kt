package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Announcement

@Composable
fun AdminAnnouncementManager(announcements: MutableList<Announcement>) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Post News") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Announcements", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(announcements) { announcement ->
                    AnnouncementAdminCard(announcement)
                }
            }
        }
    }

    if (showAddDialog) {
        AddAnnouncementDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newAnnouncement ->
                announcements.add(0, newAnnouncement) // Newest first
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AnnouncementAdminCard(announcement: Announcement) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Campaign, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(announcement.title, style = MaterialTheme.typography.titleMedium)
            }
            Text(announcement.date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(announcement.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AddAnnouncementDialog(onDismiss: () -> Unit, onAdd: (Announcement) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Announcement") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Headline") })
                OutlinedTextField(
                    value = content, 
                    onValueChange = { content = it }, 
                    label = { Text("Message") }, 
                    modifier = Modifier.height(100.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Announcement(System.currentTimeMillis().toString(), title, content, "Today"))
            }) { Text("Post") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
