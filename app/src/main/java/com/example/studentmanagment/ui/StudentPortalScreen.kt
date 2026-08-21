package com.example.studentmanagment.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studentmanagment.model.Announcement
import com.example.studentmanagment.model.Student

sealed class StudentScreen(val route: String, val label: String, val icon: ImageVector) {
    object Home : StudentScreen("home", "Home", Icons.Default.Home)
    object Schedule : StudentScreen("schedule", "Schedule", Icons.Default.CalendarMonth)
    object Profile : StudentScreen("profile", "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentPortalScreen(
    student: Student,
    announcements: List<Announcement>,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Portal") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            StudentBottomNavigation(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = StudentScreen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(StudentScreen.Home.route) { HomeScreen(student, announcements) }
            composable(StudentScreen.Schedule.route) { ScheduleScreen(student.schedule) }
            composable(StudentScreen.Profile.route) { ProfileScreen(student) }
        }
    }
}

@Composable
fun StudentBottomNavigation(navController: NavHostController) {
    val items = listOf(StudentScreen.Home, StudentScreen.Schedule, StudentScreen.Profile)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
