package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Student

@Composable
fun AdminStudentManagementScreen(students: MutableList<Student>) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Student") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Students", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(students) { student ->
                    StudentAdminCard(student)
                }
            }
        }
    }

    if (showAddDialog) {
        AddStudentDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newStudent ->
                students.add(newStudent)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun StudentAdminCard(student: Student) {
    var showEditGrade by remember { mutableStateOf(false) }
    
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(student.name, style = MaterialTheme.typography.titleMedium)
                    Text("ID: ${student.id} | ${student.stream}", style = MaterialTheme.typography.bodySmall)
                    if (student.isDropped) {
                        Text("STATUS: DROPPED", color = Color.Red, style = MaterialTheme.typography.labelSmall)
                    }
                }
                Text("Att: ${student.attendance}%", style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = { showEditGrade = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }

    if (showEditGrade) {
        AlertDialog(
            onDismissRequest = { showEditGrade = false },
            title = { Text("Quick Edit") },
            text = { Text("Advanced editing for ${student.name} coming soon.") },
            confirmButton = { Button(onClick = { showEditGrade = false }) { Text("OK") } }
        )
    }
}

@Composable
fun AddStudentDialog(onDismiss: () -> Unit, onAdd: (Student) -> Unit) {
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var stream by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Student") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("Student ID") })
                OutlinedTextField(value = stream, onValueChange = { stream = it }, label = { Text("Stream") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Student(id, name, "$id@edu.com", stream, stream, 0.0, 100.0, emptyList(), emptyList(), 5000.0, 0.0))
            }) { Text("Add") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
