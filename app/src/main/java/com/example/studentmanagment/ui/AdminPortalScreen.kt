package com.example.studentmanagment.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentmanagment.model.*

sealed class AdminScreen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : AdminScreen("admin_dashboard", "Dashboard", Icons.Default.Dashboard)
    object Students : AdminScreen("admin_students", "Students", Icons.Default.Group)
    object Faculty : AdminScreen("admin_faculty", "Faculty", Icons.Default.SupervisorAccount)
    object Courses : AdminScreen("admin_courses", "Courses", Icons.Default.Book)
    object Grading : AdminScreen("admin_grading", "Grading", Icons.Default.Grade)
    object Finance : AdminScreen("admin_finance", "Finance", Icons.Default.Payments)
    object Announcements : AdminScreen("admin_news", "News", Icons.Default.Campaign)
    object Analytics : AdminScreen("admin_analytics", "Analytics", Icons.AutoMirrored.Filled.TrendingUp)
    object Controls : AdminScreen("admin_controls", "Controls", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPortalScreen(
    students: MutableList<Student>,
    faculty: MutableList<Faculty>,
    courses: MutableList<GlobalCourse>,
    announcements: MutableList<Announcement>,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Console") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AdminScreen.Dashboard.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(AdminScreen.Dashboard.route) {
                AdminDashboard(onSelectModule = { navController.navigate(it.route) })
            }
            composable(AdminScreen.Students.route) { AdminStudentManagementScreen(students) }
            composable(AdminScreen.Faculty.route) { AdminFacultyScreen(faculty) }
            composable(AdminScreen.Courses.route) { AdminCourseManagementScreen(courses) }
            composable(AdminScreen.Grading.route) { 
                LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text("Grading Hub", style = MaterialTheme.typography.headlineMedium)
                        Spacer(Modifier.height(16.dp))
                    }
                    items(students) { s -> StudentAdminCard(s) }
                }
            }
            composable(AdminScreen.Finance.route) { AdminFinanceDashboard(students) }
            composable(AdminScreen.Announcements.route) { AdminAnnouncementManager(announcements) }
            composable(AdminScreen.Analytics.route) { AdminAnalyticsScreen(students) }
            composable(AdminScreen.Controls.route) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("System Controls", style = MaterialTheme.typography.headlineMedium)
                    Button(onClick = {
                        students.forEachIndexed { i, s -> if (s.attendance < 75) students[i] = s.copy(isDropped = true) }
                    }) { Text("Run Batch Attendance Check") }
                }
            }
        }
    }
}

@Composable
fun AdminDashboard(onSelectModule: (AdminScreen) -> Unit) {
    val modules = listOf(
        AdminScreen.Students, AdminScreen.Faculty, AdminScreen.Courses,
        AdminScreen.Grading, AdminScreen.Finance, AdminScreen.Announcements,
        AdminScreen.Analytics, AdminScreen.Controls
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(modules) { module ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().aspectRatio(1.2f).clickable { onSelectModule(module) }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(module.icon, contentDescription = null, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(8.dp))
                    Text(module.label, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
