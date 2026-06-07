package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.model.User
import com.example.myapplication.model.UserType
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileDetailScreen(
    navController: NavController, 
    viewModel: MentorNetViewModel, 
    userId: String, 
    userType: String,
    isConnection: Boolean = false
) {
    val mentors by viewModel.mentors.collectAsState()
    val friends by viewModel.friends.collectAsState()
    
    val user = if (userType == "MENTOR") {
        mentors.find { it.id == userId }
    } else {
        friends.find { it.id == userId }
    }

    if (user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Kullanıcı bulunamadı")
        }
        return
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(EsenlerBlue),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = Color.White)
                    }
                    Text(
                        if (user.userType == UserType.MENTOR) "MENTÖR PROFİLİ" else "ARKADAŞ PROFİLİ",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )
                    IconButton(onClick = { /* Mesaj Gönder */ }) {
                        Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Mesaj", tint = Color.White)
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
                .background(Color(0xFFF5F7FA))
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, if (user.userType == UserType.MENTOR) EsenlerOrange else EsenlerBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp),
                    tint = EsenlerGray.copy(alpha = 0.2f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${user.name} ${user.surname}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            if (user.userType == UserType.MENTOR) {
                Surface(
                    color = EsenlerOrange.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "KIDEMLİ MENTÖR",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = EsenlerOrange,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (user.userType == UserType.MENTOR) {
                MentorProfileContent(user)
            } else {
                StudentProfileContent(user)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            if (isConnection) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { /* Mesaj At */ },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EsenlerBlue)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Chat, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("MESAJ AT", fontWeight = FontWeight.Bold)
                    }
                    
                    if (user.userType == UserType.MENTOR) {
                        Button(
                            onClick = { /* Görüşme Planla */ },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EsenlerOrange)
                        ) {
                            Icon(Icons.Default.Event, null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("PLANLA", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                Button(
                    onClick = { /* Bağlantı İsteği */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EsenlerBlue)
                ) {
                    Icon(Icons.Default.PersonAdd, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("BAĞLANTI KUR", fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun MentorProfileContent(user: User) {
    // Uzmanlık Alanları
    DetailSectionTitle("UZMANLIK ALANLARI")
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        FlowRow(modifier = Modifier.padding(16.dp)) {
            user.interests.forEach { interest ->
                SuggestionChip(
                    onClick = { },
                    label = { Text(interest) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }

    // Deneyim
    DetailSectionTitle("KARİYER VE DENEYİM")
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(Icons.Default.Work, "Tecrübe", user.bio.split("-").firstOrNull()?.trim() ?: "10+ Yıl")
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.3f))
            InfoRow(Icons.Default.Mail, "E-Posta", user.email)
        }
    }
}

@Composable
fun StudentProfileContent(user: User) {
    // Eğitim
    DetailSectionTitle("EĞİTİM BİLGİLERİ")
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(Icons.Default.School, "Üniversite", user.university.ifEmpty { "Belirtilmemiş" })
            InfoRow(Icons.Default.Book, "Bölüm", user.department.ifEmpty { "Belirtilmemiş" })
            InfoRow(Icons.Default.Grade, "Eğitim Seviyesi", user.education.ifEmpty { "Öğrenci" })
        }
    }

    // İlgi Alanları
    DetailSectionTitle("İLGİ ALANLARI VE HEDEFLER")
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("İlgi Alanları:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = EsenlerGray)
            Text(user.interests.joinToString(", "), fontSize = 14.sp, color = Color.Black)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text("Hedefler:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = EsenlerGray)
            Text(user.careerGoals.ifEmpty { "Harika projeler geliştirmek!" }, fontSize = 14.sp, color = Color.Black)
        }
    }
}

@Composable
fun DetailSectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 8.dp),
        color = EsenlerBlue,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, null, tint = EsenlerBlue, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontSize = 11.sp, color = EsenlerGray.copy(alpha = 0.6f))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.FlowRow(modifier = modifier) {
        content()
    }
}
