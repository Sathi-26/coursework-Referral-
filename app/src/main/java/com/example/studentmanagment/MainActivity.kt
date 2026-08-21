package com.example.studentmanagment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.studentmanagment.model.*
import com.example.studentmanagment.ui.*
import com.example.studentmanagment.ui.theme.StudentManagmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentManagmentTheme {
                var loggedInStudentId by remember { mutableStateOf<String?>(null) }
                var isAdmin by remember { mutableStateOf(false) }

                // Global Mock State
                val students = remember {
                    mutableStateListOf(
                        Student("S123", "John Doe", "john@edu.com", "CS", "Science", 3.8, 85.0, 
                            listOf(Course("Android Dev", "A", 4)), emptyList(), 5000.0, 4200.0),
                        Student("S456", "Jane Smith", "jane@edu.com", "Business", "Commerce", 3.5, 60.0, 
                            emptyList(), emptyList(), 5000.0, 5000.0)
                    )
                }
                val facultyList = remember { mutableStateListOf<Faculty>() }
                val globalCourses = remember { mutableStateListOf<GlobalCourse>() }
                val announcements = remember { 
                    mutableStateListOf(
                        Announcement("1", "Welcome", "Welcome to the new portal!", "Today")
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimatedContent(
                        targetState = loggedInStudentId,
                        transitionSpec = {
                            if (targetState != null) {
                                (slideInHorizontally { it } + fadeIn()).togetherWith(
                                    slideOutHorizontally { -it } + fadeOut())
                            } else {
                                (slideInHorizontally { -it } + fadeIn()).togetherWith(
                                    slideOutHorizontally { it } + fadeOut())
                            }.using(SizeTransform(clip = false))
                        },
                        label = "ScreenTransition",
                        modifier = Modifier.padding(innerPadding)
                    ) { studentId ->
                        if (studentId == null) {
                            LoginScreen(onLoginSuccess = { id, admin -> 
                                loggedInStudentId = id 
                                isAdmin = admin
                            })
                        } else if (isAdmin) {
                            AdminPortalScreen(
                                students = students,
                                faculty = facultyList,
                                courses = globalCourses,
                                announcements = announcements,
                                onLogout = { loggedInStudentId = null }
                            )
                        } else {
                            val currentStudent = students.find { it.id == studentId } ?: students[0]
                            StudentPortalScreen(
                                student = currentStudent,
                                announcements = announcements,
                                onLogout = { loggedInStudentId = null }
                            )
                        }
                    }
                }
            }
        }
    }
}
