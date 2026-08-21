package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.studentmanagment.model.Student

@Composable
fun AdminFinanceDashboard(students: List<Student>) {
    val totalRevenue = students.sumOf { it.paidFees }
    val totalOutstanding = students.sumOf { it.balance }
    val collectionRate = if (totalRevenue + totalOutstanding > 0) (totalRevenue / (totalRevenue + totalOutstanding)) * 100 else 0.0

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Financial Overview", style = MaterialTheme.typography.headlineMedium)
        }

        item {
            FinanceStatCard("Total Revenue Collected", "$${totalRevenue}", Icons.Default.AttachMoney, MaterialTheme.colorScheme.primaryContainer)
        }

        item {
            FinanceStatCard("Total Outstanding Balance", "$${totalOutstanding}", Icons.Default.MoneyOff, MaterialTheme.colorScheme.errorContainer)
        }

        item {
            FinanceStatCard("Collection Rate", "${String.format("%.1f", collectionRate)}%", Icons.Default.TrendingUp, MaterialTheme.colorScheme.secondaryContainer)
        }

        item {
            Text("Student Fee Status", style = MaterialTheme.typography.titleLarge)
        }

        items(students.size) { index ->
            val student = students[index]
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(student.name, style = MaterialTheme.typography.titleSmall)
                        Text("Paid: $${student.paidFees}", style = MaterialTheme.typography.bodySmall)
                    }
                    Text("Bal: $${student.balance}", color = if (student.balance > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun FinanceStatCard(label: String, value: String, icon: ImageVector, containerColor: Color) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(48.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelMedium)
                Text(value, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
