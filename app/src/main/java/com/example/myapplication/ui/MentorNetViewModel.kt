package com.example.myapplication.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DatabaseHelper
import com.example.myapplication.model.User
import com.example.myapplication.model.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MentorNetViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val TAG = "MentorNetViewModel"

    private val _currentUser = MutableStateFlow(
        User(
            id = "1",
            name = "Ahmet",
            surname = "Yılmaz",
            tcNumber = "12345678901",
            userType = UserType.YOUTH,
            points = 150,
            isVerified = true,
            age = 17,
            interests = listOf("Yazılım", "Teknofest", "İngilizce"),
            education = "Lisans",
            university = "Esenler Üniversitesi",
            department = "Yazılım Mühendisliği",
            learningGoals = "Mobil Geliştirme",
            careerGoals = "Android Developer Olmak",
            weeklyAvailability = 10
        )
    )
    val currentUser: StateFlow<User> = _currentUser

    private val _mentors = MutableStateFlow<List<User>>(emptyList())
    val mentors: StateFlow<List<User>> = _mentors

    private val _friends = MutableStateFlow<List<User>>(emptyList())
    val friends: StateFlow<List<User>> = _friends

    // Theme and Language Settings
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _isEnglish = MutableStateFlow(false)
    val isEnglish: StateFlow<Boolean> = _isEnglish

    init {
        loadData()
    }

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun setLanguage(isEnglish: Boolean) {
        _isEnglish.value = isEnglish
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                val mentorList = withContext(Dispatchers.IO) {
                    dbHelper.getAllMentors()
                }
                val studentList = withContext(Dispatchers.IO) {
                    dbHelper.getAllStudents()
                }

                _mentors.value = mentorList
                _friends.value = studentList
                
                Log.d(TAG, "Veriler veritabanından başarıyla yüklendi. Mentör: ${mentorList.size}, Öğrenci: ${studentList.size}")
            } catch (e: Exception) {
                Log.e(TAG, "Veritabanından veri çekme hatası: ${e.message}", e)
            }
        }
    }

    fun updateUserInfo(
        name: String, 
        surname: String, 
        tc: String, 
        education: String,
        university: String,
        department: String,
        interests: String,
        learningGoals: String,
        careerGoals: String,
        weeklyAvailability: Int
    ) {
        _currentUser.value = _currentUser.value.copy(
            name = name,
            surname = surname,
            tcNumber = tc,
            education = education,
            university = university,
            department = department,
            interests = interests.split(",").map { it.trim() },
            learningGoals = learningGoals,
            careerGoals = careerGoals,
            weeklyAvailability = weeklyAvailability
        )
    }

    fun addPoints(amount: Int) {
        val current = _currentUser.value
        _currentUser.value = current.copy(points = current.points + amount)
    }
}
