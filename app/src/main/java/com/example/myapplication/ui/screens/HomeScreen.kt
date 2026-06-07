package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.getAppStrings
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val strings = getAppStrings(viewModel)

    // UYARI MESAJLARI (TOAST) İÇİN CONTEXT BURADA TANIMLANDI
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, viewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Small Rounded Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(EsenlerBlue),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        strings.appName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 1.2.sp
                    )
                    IconButton(onClick = { navController.navigate("app_settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = strings.settings, tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Headline Section
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (strings.language == "App Language") "Esenler Municipality Youth Network Platform" else "Esenler Belediyesi Gençlik Network Platformu",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (strings.language == "App Language") "A digital platform where youth in Esenler safely find mentors, get project partners, and grow their networks." else "Esenler'deki gençlerin güvenle mentor bulduğu, proje ortağı edindiği ve networkünü büyüttüğü dijital platform.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Gradient Action Button (YAPAY ZEKA BURAYA BAĞLANDI)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(56.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(EsenlerOrange, Color(0xFFFFB74D))
                        )
                    )
                    .clickable {
                        navController.navigate("ai_matching")
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        strings.findMentor,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Grid of Features
            val features = listOf(
                FeatureItem(strings.profile, Icons.Default.AccountCircle, "profile"),
                FeatureItem(strings.network, Icons.Default.People, "friends"),
                FeatureItem(strings.myMentor, Icons.Default.School, "mentors_list"),
                FeatureItem(strings.findProjectPartner, Icons.Default.RocketLaunch, "projects"),
                FeatureItem(strings.safeMessage, Icons.AutoMirrored.Filled.Chat, "safety_info"),
                FeatureItem(strings.events, Icons.Default.Event, "events")
            )

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                features.chunked(2).forEach { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        rowItems.forEach { item ->
                            Box(modifier = Modifier.weight(1f)) {
                                FeatureCardNew(item) {
                                    navController.navigate(item.route)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun FeatureCardNew(feature: FeatureItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = EsenlerBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = feature.title,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, viewModel: MentorNetViewModel) {
    val strings = getAppStrings(viewModel)
    val items = listOf(
        NavigationItem(strings.home, "home", Icons.Default.Home),
        NavigationItem(strings.messages, "messages", Icons.AutoMirrored.Filled.Chat),
        NavigationItem(strings.myAccount, "settings", Icons.Default.ManageAccounts)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title, fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = EsenlerBlue,
                    selectedTextColor = EsenlerBlue,
                    unselectedIconColor = Color.Gray.copy(alpha = 0.6f),
                    unselectedTextColor = Color.Gray.copy(alpha = 0.6f),
                    indicatorColor = EsenlerBlue.copy(alpha = 0.1f)
                )
            )
        }
    }
}

data class NavigationItem(val title: String, val route: String, val icon: ImageVector)

data class FeatureItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)