package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Student

@Composable
fun ProfileScreen(student: Student) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(student.name, style = MaterialTheme.typography.headlineMedium)
            Text(student.major, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
        }

        item {
            ProfileInfoSection(student)
        }

        item {
            FinancialSection(student)
        }
    }
}

@Composable
fun ProfileInfoSection(student: Student) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Personal Information", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))
            InfoRow(Icons.Default.Badge, "Student ID", student.id)
            InfoRow(Icons.Default.Email, "Email", student.email)
        }
    }
}

@Composable
fun FinancialSection(student: Student) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (student.balance > 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Financial Status", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))
            InfoRow(Icons.Default.Payments, "Total Fees", "$${student.totalFees}")
            InfoRow(Icons.Default.CreditCard, "Paid", "$${student.paidFees}")
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            InfoRow(
                Icons.Default.Warning, 
                "Outstanding Balance", 
                "$${student.balance}",
                valueColor = if (student.balance > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String, valueColor: Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        Column {
            Text(label, style = MaterialTheme.typography.bodySmall)
            Text(value, style = MaterialTheme.typography.bodyLarge, color = valueColor)
        }
    }
}
