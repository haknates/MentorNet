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
import androidx.compose.runtime.*
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
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiResultScreen(navController: NavController, viewModel: MentorNetViewModel, searchType: String, sector: String) {
    val mentors by viewModel.mentors.collectAsState()
    val friends by viewModel.friends.collectAsState()
    
    val results = if (searchType == "Mentör" || searchType == "Mentor") {
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
            // AI Header Card
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
                                "Yapay Zeka\n${if (searchType.startsWith("Ment")) "Mentör" else "Arkadaş"} Analizi",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 28.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Gemini 1.5 Flash Aktif", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                            }
                        }
                        Icon(
                            Icons.Default.Psychology,
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
                if (results.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("Uygun aday bulunamadı.", color = EsenlerGray)
                        }
                    }
                } else {
                    items(results) { user ->
                        AiMatchCard(user, searchType, viewModel) {
                            navController.navigate("user_detail/${user.id}/${user.userType.name}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AiMatchCard(user: User, searchType: String, viewModel: MentorNetViewModel, onClick: () -> Unit) {
    val currentUser by viewModel.currentUser.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var aiAnalysis by remember { mutableStateOf<String?>(null) }
    var isAnalyzing by remember { mutableStateOf(false) }
    
    // Gemini Model setup with your key
    val generativeModel = remember {
        GenerativeModel(
            modelName = "gemini-3.5-flash",
            apiKey = "AQ.Ab8RN6IGQFj6midQ3PSeiteYyPptFBZEWny2H73qh75GsGUKJA"
        )
    }

    LaunchedEffect(user.id) {
        isAnalyzing = true
        coroutineScope.launch {
            try {
                val prompt = """
                    Analiz et ve iki kullanıcı arasındaki uyumu açıkla.
                    Kullanıcı 1 (Ben): ${currentUser.name}, Hedefler: ${currentUser.careerGoals}, İlgi: ${currentUser.interests.joinToString()}
                    Kullanıcı 2 (${if (user.userType == UserType.MENTOR) "Mentör" else "Arkadaş"}): ${user.name}, Uzmanlık/İlgi: ${user.interests.joinToString()}, Biyografi: ${user.bio}
                    
                    Lütfen sadece 2 kısa cümle ile uyum oranını (%) ve nedenini yaz.
                """.trimIndent()
                
                val response = generativeModel.generateContent(prompt)
                aiAnalysis = response.text ?: "Analiz yapılamadı."
            } catch (e: Exception) {
                aiAnalysis = "Bağlantı sorunu: ${e.localizedMessage}"
                android.util.Log.e("AiMatchCard", "Gemini Hatası", e)
            } finally {
                isAnalyzing = false
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(EsenlerBlue.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, null, tint = EsenlerBlue)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("${user.name} ${user.surname}", fontWeight = FontWeight.Bold, color = EsenlerGray)
                    Text(
                        if (user.userType == UserType.MENTOR) "Mentör" else "Öğrenci",
                        fontSize = 12.sp,
                        color = EsenlerGray.copy(alpha = 0.6f)
                    )
                }
                
                Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(12.dp))
            
            // AI Analysis Section
            Surface(
                color = Color(0xFFF8F9FF),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, null, tint = EsenlerOrange, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Yapay Zeka Analizi", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = EsenlerBlue)
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    if (isAnalyzing) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth().height(2.dp),
                            color = EsenlerOrange,
                            trackColor = EsenlerOrange.copy(alpha = 0.1f)
                        )
                    } else {
                        Text(
                            text = aiAnalysis ?: "Analiz hazırlanıyor...",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
