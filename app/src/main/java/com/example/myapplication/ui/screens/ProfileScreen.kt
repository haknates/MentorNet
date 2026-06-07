package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.getAppStrings
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val user by viewModel.currentUser.collectAsState()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val strings = getAppStrings(viewModel)
    val isEnglish by viewModel.isEnglish.collectAsState()

    var name by remember { mutableStateOf(user.name) }
    var surname by remember { mutableStateOf(user.surname) }
    var tcNumber by remember { mutableStateOf(user.tcNumber) }
    var education by remember { mutableStateOf(user.education) }
    var university by remember { mutableStateOf(user.university) }
    var department by remember { mutableStateOf(user.department) }
    var interests by remember { mutableStateOf(user.interests.joinToString(", ")) }
    var learningGoals by remember { mutableStateOf(user.learningGoals) }
    var careerGoals by remember { mutableStateOf(user.careerGoals) }
    var weeklyAvailability by remember { mutableStateOf(user.weeklyAvailability.toString()) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(EsenlerBlue),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.back, tint = Color.White)
                    }
                    Text(
                        strings.profile.uppercase(),
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )
                    TextButton(onClick = {
                        viewModel.updateUserInfo(
                            name, surname, tcNumber, education, university, department, 
                            interests, learningGoals, careerGoals, weeklyAvailability.toIntOrNull() ?: 0
                        )
                        navController.popBackStack()
                    }) {
                        Text(strings.save, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController, viewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image Section
            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(3.dp, EsenlerOrange.copy(alpha = 0.8f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(70.dp),
                        tint = EsenlerGray.copy(alpha = 0.2f)
                    )
                }
                Surface(
                    onClick = { /* Pick Image */ },
                    shape = CircleShape,
                    color = EsenlerOrange,
                    modifier = Modifier.size(36.dp).border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = strings.edit,
                        modifier = Modifier.padding(8.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sections
            ProfileSectionTitle(if (isEnglish) "PERSONAL INFORMATION" else "KİŞİSEL BİLGİLER")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileField(strings.name, name) { name = it }
                    ProfileField(strings.surname, surname) { surname = it }
                    ProfileField(strings.tcNo, tcNumber) { tcNumber = it }
                }
            }

            ProfileSectionTitle(if (isEnglish) "EDUCATION AND GOALS" else "EĞİTİM VE HEDEFLER")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileField(strings.education, education) { education = it }
                    ProfileField(strings.university, university) { university = it }
                    ProfileField(strings.department, department) { department = it }
                    ProfileField(strings.weeklyTime, weeklyAvailability, KeyboardType.Number) { weeklyAvailability = it }
                }
            }

            ProfileSectionTitle(if (isEnglish) "INTERESTS AND VISION" else "İLGİ ALANLARI VE VİZYON")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileField(strings.interests, interests) { interests = it }
                    ProfileField(strings.learnGoals, learningGoals) { learningGoals = it }
                    ProfileField(strings.goals, careerGoals) { careerGoals = it }
                }
            }

            ProfileSectionTitle(strings.documents)
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    UploadCard(if (isEnglish) "Criminal Record" else "Adli Sicil Kaydı", user.criminalRecordUrl != null, isEnglish) { /* Upload */ }
                    UploadCard(if (isEnglish) "CV / Resume" else "CV / Özgeçmiş", user.cvUrl != null, isEnglish) { /* Upload */ }
                    UploadCard(if (isEnglish) "Student Certificate" else "Öğrenci Belgesi", user.diplomaUrl != null, isEnglish) { /* Upload */ }
                }
            }

            if (user.age < 18 && user.age > 0) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = EsenlerOrange)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            if (isEnglish) "Security Notice: Under 18 profiles are under parental supervision." else "Güvenlik Bildirimi: 18 yaş altı profiller ebeveyn gözetimindedir.", 
                            color = EsenlerGray, fontSize = 12.sp, fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 8.dp),
        color = EsenlerBlue,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

@Composable
fun ProfileField(label: String, value: String, keyboardType: KeyboardType = KeyboardType.Text, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = EsenlerBlue,
            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
        ),
        singleLine = label != "Gelecek Hedefleri" && label != "Öğrenmek İstedikleri" && label != "Future Goals" && label != "What They Want to Learn"
    )
}

@Composable
fun UploadCard(title: String, isUploaded: Boolean, isEnglish: Boolean, onUpload: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        onClick = onUpload,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (isUploaded) Icons.Default.CheckCircle else Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = if (isUploaded) Color(0xFF4CAF50) else EsenlerBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
            }
            Text(
                if (isUploaded) (if (isEnglish) "Uploaded" else "Yüklendi") else (if (isEnglish) "Select" else "Seç"),
                color = if (isUploaded) Color(0xFF4CAF50) else EsenlerBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
