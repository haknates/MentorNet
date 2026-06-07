package com.example.myapplication.model

enum class UserType {
    YOUTH, MENTOR, MUNICIPALITY_EMPLOYEE
}

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val tcNumber: String = "",
    val email: String = "",
    val userType: UserType = UserType.YOUTH,
    val age: Int = 0,
    val interests: List<String> = emptyList(),
    val points: Int = 0,
    val isVerified: Boolean = false,
    val bio: String = "",
    val profileImageUrl: String = "",
    val education: String = "",
    val university: String = "",
    val department: String = "",
    val learningGoals: String = "",
    val careerGoals: String = "",
    val weeklyAvailability: Int = 0,
    val cvUrl: String? = null,
    val criminalRecordUrl: String? = null,
    val diplomaUrl: String? = null
)

data class Project(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val creatorId: String = "",
    val category: String = "", // e.g., Teknofest, Tübitak
    val members: List<String> = emptyList(), // List of User IDs
    val requiredInterests: List<String> = emptyList()
)

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val location: String = "Esenler Municipality"
)
