package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.GlobalCourse

@Composable
fun AdminCourseManagementScreen(courses: MutableList<GlobalCourse>) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("New Course") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Course Catalog", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(courses) { course ->
                    GlobalCourseCard(course)
                }
            }
        }
    }

    if (showAddDialog) {
        AddCourseDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newCourse ->
                courses.add(newCourse)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun GlobalCourseCard(course: GlobalCourse) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(Icons.Default.Book, contentDescription = null, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
            Column(modifier = Modifier.weight(1f)) {
                Text(course.name, style = MaterialTheme.typography.titleMedium)
                Text("${course.code} | ${course.department}", style = MaterialTheme.typography.bodySmall)
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    "${course.credits} Cr",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun AddCourseDialog(onDismiss: () -> Unit, onAdd: (GlobalCourse) -> Unit) {
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var credits by remember { mutableStateOf("") }
    var dept by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Global Course") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Course Name") })
                OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Course Code") })
                OutlinedTextField(value = credits, onValueChange = { credits = it }, label = { Text("Credits") })
                OutlinedTextField(value = dept, onValueChange = { dept = it }, label = { Text("Department") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(GlobalCourse(code, name, credits.toIntOrNull() ?: 0, dept))
            }) { Text("Create") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
