package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.model.User
import com.example.myapplication.model.UserType
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerGreen
import com.example.myapplication.ui.theme.EsenlerOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiResultScreen(navController: NavController, viewModel: MentorNetViewModel, searchType: String, sector: String) {
    val mentors by viewModel.mentors.collectAsState()
    val friends by viewModel.friends.collectAsState()
    
    val results = if (searchType == "Mentör") {
        mentors.filter { it.interests.any { interest -> interest.contains(sector, ignoreCase = true) } || sector == "" }
    } else {
        friends.filter { it.interests.any { interest -> interest.contains(sector, ignoreCase = true) } || sector == "" }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Eşleşmeleri", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = EsenlerBlue),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Geri", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add new */ }, containerColor = EsenlerBlue.copy(alpha = 0.7f), contentColor = Color.White) {
                Icon(Icons.Default.Add, null)
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
                .background(Color(0xFFF5F9FA))
        ) {
            // AI Header Card (as in image)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(EsenlerOrange, Color(0xFFFFB74D))
                            )
                        )
                        .padding(24.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Yapay Zeka\nMentör Eşleştirme",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 28.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.TrackChanges, null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Hedef Analizi", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "Hedeflerinize ve becerilerinize göre sizin için mükemmel mentörleri bulduk.",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                        Icon(
                            Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.3f),
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(results) { user ->
                    AiMatchCard(user, searchType) {
                        navController.navigate("user_detail/${user.id}/${user.userType.name}")
                    }
                }
                
                // If no results, show all as fallback
                if (results.isEmpty()) {
                    val fallbackList = if (searchType == "Mentör") mentors else friends
                    items(fallbackList) { user ->
                        AiMatchCard(user, searchType) {
                            navController.navigate("user_detail/${user.id}/${user.userType.name}?isConnection=false")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AiMatchCard(user: User, searchType: String, onClick: () -> Unit) {
    val matchPercentage = (85..98).random()
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image Placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(EsenlerBlue.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = EsenlerBlue, modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${user.name} ${user.surname}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = EsenlerGray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = EsenlerGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Eşleşme: %$matchPercentage",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            color = EsenlerGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Text(
                    if (user.userType == UserType.MENTOR) "(${user.interests.firstOrNull() ?: "Uzman"})" else "(Öğrenci)",
                    fontSize = 14.sp,
                    color = EsenlerGray.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Work, null, tint = EsenlerGray.copy(alpha = 0.5f), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        if (user.userType == UserType.MENTOR) user.bio.split("-").firstOrNull()?.trim() ?: "10+ Yıl Tecrübe" else "Öğrenci",
                        fontSize = 12.sp,
                        color = EsenlerGray.copy(alpha = 0.6f)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Hub, null, tint = EsenlerGray.copy(alpha = 0.5f), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        user.interests.take(3).joinToString(", "),
                        fontSize = 12.sp,
                        color = EsenlerGray.copy(alpha = 0.6f)
                    )
                }
            }

            Icon(
                if (user.userType == UserType.MENTOR) Icons.Default.Lightbulb else Icons.Default.Computer,
                contentDescription = null,
                tint = EsenlerOrange,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
