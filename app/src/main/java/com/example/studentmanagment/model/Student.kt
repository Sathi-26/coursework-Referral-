package com.example.studentmanagment.model

data class Student(
    val id: String,
    val name: String,
    val email: String,
    val major: String,
    val stream: String = "Engineering",
    val gpa: Double,
    val attendance: Double,
    val courses: List<Course>,
    val schedule: List<ScheduleItem>,
    val totalFees: Double,
    val paidFees: Double,
    val isDropped: Boolean = false
) {
    val balance: Double get() = totalFees - paidFees
}

data class Course(
    val name: String,
    val grade: String,
    val credits: Int
)

data class ScheduleItem(
    val day: String,
    val time: String,
    val subject: String,
    val room: String
)

data class Faculty(
    val id: String,
    val name: String,
    val department: String,
    val email: String,
    val joinedDate: String
)

data class GlobalCourse(
    val code: String,
    val name: String,
    val credits: Int,
    val department: String
)

data class Announcement(
    val id: String,
    val title: String,
    val content: String,
    val date: String,
    val priority: String = "Normal"
)
