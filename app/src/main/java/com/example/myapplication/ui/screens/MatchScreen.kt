package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val mentors by viewModel.mentors.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    // Simple matching: find mentors with at least one common interest
    val matchedMentors = mentors.filter { mentor ->
        mentor.interests.any { it.trim() in user.interests }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(EsenlerBlue),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = Color.White)
                    }
                    Text(
                        "EŞLEŞTİRME",
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.size(48.dp))
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
                .padding(16.dp)
        ) {
            Text(
                "İlgi alanlarınıza göre önerilen mentörler:",
                color = EsenlerGray,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            if (matchedMentors.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Şu an için eşleşen mentör bulunamadı.", color = Color.Gray)
                }
            }

            matchedMentors.forEach { mentor ->
                Card(
                    modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${mentor.name} ${mentor.surname}", style = MaterialTheme.typography.titleMedium, color = EsenlerGray, fontWeight = FontWeight.Bold)
                        Text(mentor.bio, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Uzmanlık: ${mentor.interests.joinToString(", ")}", fontSize = 12.sp, color = EsenlerBlue)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row {
                            Button(
                                onClick = { /* Message mentor */ },
                                colors = ButtonDefaults.buttonColors(containerColor = EsenlerBlue)
                            ) {
                                Text("Mesaj At", color = Color.White)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedButton(onClick = { /* Report */ }) {
                                Text("Şikayet Et", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
