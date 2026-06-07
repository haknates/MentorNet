package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun MessagesScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val chats = listOf(
        ChatPreview("Mehmet Öz", "Yarın mentörlük seansı yapabilir miyiz?", "14:20", 2),
        ChatPreview("Zeynep Kaya", "Proje dosyasını gönderdim.", "Dün", 0),
        ChatPreview("Emre Yılmaz", "Teşekkürler!", "Pazartesi", 0)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mesajlar", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = EsenlerBlue),
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Ara", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, viewModel)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(chats) { chat ->
                ChatItem(chat) {
                    // Navigate to individual chat screen
                }
                HorizontalDivider(color = EsenlerGray.copy(alpha = 0.1f), thickness = 1.dp)
            }
        }
    }
}

data class ChatPreview(val name: String, val lastMsg: String, val time: String, val unreadCount: Int)

@Composable
fun ChatItem(chat: ChatPreview, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(EsenlerBlue.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(chat.name.take(1), color = EsenlerBlue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(chat.name, color = EsenlerGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(chat.time, color = EsenlerGray.copy(alpha = 0.6f), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    chat.lastMsg,
                    color = EsenlerGray.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                if (chat.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(EsenlerBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(chat.unreadCount.toString(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
