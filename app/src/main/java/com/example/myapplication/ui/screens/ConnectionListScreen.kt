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
fun ConnectionListScreen(navController: NavController, title: String, viewModel: MentorNetViewModel) {
    val mentors by viewModel.mentors.collectAsState()
    val friends by viewModel.friends.collectAsState()
    
    val connections = if (title == "Mentörüm") mentors else friends

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
                        title.uppercase(),
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F9FA)),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(connections) { user ->
                AiMatchCard(user = user, searchType = if (title == "Mentörüm") "Mentör" else "Arkadaş") {
                    navController.navigate("user_detail/${user.id}/${user.userType.name}?isConnection=true")
                }
            }
        }
    }
}
