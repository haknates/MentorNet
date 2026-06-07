package com.example.myapplication.ui.screens

import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerOrange
import com.example.myapplication.ui.theme.EsenlerGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(navController: NavController, viewModel: MentorNetViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Belediye Etkinlikleri", "Takvimim")

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(EsenlerBlue),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Geri", tint = Color.White)
                        }
                        Text(
                            "ETKİNLİKLER",
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(48.dp))
                    }
                    
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = EsenlerOrange,
                                height = 3.dp
                            )
                        },
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = { 
                                    Text(
                                        title, 
                                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 14.sp
                                    ) 
                                }
                            )
                        }
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
        ) {
            if (selectedTab == 0) {
                MunicipalEventsList()
            } else {
                MyCalendarList()
            }
        }
    }
}

@Composable
fun MunicipalEventsList() {
    val events = listOf(
        MunicipalEvent("Teknoloji ve Girişimcilik Zirvesi", "10 Temmuz 2026", "Esenler Kültür Merkezi", "Ücretsiz"),
        MunicipalEvent("Yapay Zeka Atölyesi", "15 Temmuz 2026", "Esenler Gençlik Merkezi", "Kayıtlı"),
        MunicipalEvent("Outdoor Networking Pikniği", "20 Temmuz 2026", "Esenler Bölge Parkı", "Ücretsiz"),
        MunicipalEvent("Kodlama Maratonu (Hackathon)", "05 Ağustos 2026", "Esenler Teknopark", "Ödüllü")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(events) { event ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(EsenlerGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Celebration, null, tint = EsenlerGreen)
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(event.title, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 15.sp)
                        Text("${event.date} • ${event.location}", color = EsenlerGray.copy(alpha = 0.6f), fontSize = 12.sp)
                    }
                    
                    TextButton(onClick = { /* Kaydol */ }) {
                        Text(if (event.status == "Kayıtlı") "KAYITLI" else "KAYDOL", color = if (event.status == "Kayıtlı") EsenlerGray else EsenlerBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun MyCalendarList() {
    val myEvents = listOf(
        CalendarEvent("Mentör Görüşmesi", "Caner Yılmaz ile Kariyer Planlama", "02.07.2026", "14:00 - 15:00", EventType.MENTOR),
        CalendarEvent("Network Görüşmesi", "Ahmet ile Yazılım hakkında görüşme", "05.07.2026", "15:00 - 15:30", EventType.NETWORK),
        CalendarEvent("Belediye Etkinliği", "Yapay Zeka Atölyesi", "15.07.2026", "10:00 - 17:00", EventType.MUNICIPAL),
        CalendarEvent("Mentör Görüşmesi", "Selin Kaya ile Staj Danışmanlığı", "18.07.2026", "11:00 - 11:45", EventType.MENTOR)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "YAKLAŞAN GÖRÜŞMELER",
                style = MaterialTheme.typography.labelLarge,
                color = EsenlerGray.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(myEvents) { event ->
            val color = when(event.type) {
                EventType.MENTOR -> EsenlerOrange
                EventType.NETWORK -> EsenlerBlue
                EventType.MUNICIPAL -> EsenlerGreen
            }
            
            val icon = when(event.type) {
                EventType.MENTOR -> Icons.Default.School
                EventType.NETWORK -> Icons.Default.People
                EventType.MUNICIPAL -> Icons.Default.AccountBalance
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    // Left Color Bar
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(6.dp)
                            .background(color)
                    )
                    
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(icon, null, tint = color, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(event.category, color = color, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(event.title, fontWeight = FontWeight.ExtraBold, color = Color.Black, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Event, null, tint = EsenlerGray.copy(alpha = 0.5f), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(event.date, color = EsenlerGray, fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(Icons.Default.Schedule, null, tint = EsenlerGray.copy(alpha = 0.5f), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(event.time, color = EsenlerGray, fontSize = 13.sp)
                            }
                        }
                        
                        IconButton(onClick = { /* Görüşmeye Katıl/Detay */ }) {
                            Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

enum class EventType {
    MENTOR, NETWORK, MUNICIPAL
}

data class MunicipalEvent(val title: String, val date: String, val location: String, val status: String)
data class CalendarEvent(val category: String, val title: String, val date: String, val time: String, val type: EventType)
