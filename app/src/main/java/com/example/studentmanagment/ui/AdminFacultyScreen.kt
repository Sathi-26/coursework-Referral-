package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Faculty

@Composable
fun AdminFacultyScreen(facultyList: MutableList<Faculty>) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Faculty") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Faculty Management", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(facultyList) { faculty ->
                    FacultyCard(faculty)
                }
            }
        }
    }

    if (showAddDialog) {
        AddFacultyDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newFaculty ->
                facultyList.add(newFaculty)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun FacultyCard(faculty: Faculty) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(faculty.name, style = MaterialTheme.typography.titleLarge)
            Text(faculty.department, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Badge, contentDescription = null, modifier = Modifier.size(16.dp))
                Text("ID: ${faculty.id}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                Text(faculty.email, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AddFacultyDialog(onDismiss: () -> Unit, onAdd: (Faculty) -> Unit) {
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var dept by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Faculty") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") })
                OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("Faculty ID") })
                OutlinedTextField(value = dept, onValueChange = { dept = it }, label = { Text("Department") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Faculty(id, name, dept, email, "2023-10-01"))
            }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
