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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.getAppStrings
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val user by viewModel.currentUser.collectAsState()
    val scrollState = rememberScrollState()
    val strings = getAppStrings(viewModel)

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.back, tint = Color.White)
                    }
                    Text(
                        strings.myAccount.uppercase(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { navController.navigate("profile") },
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = strings.edit, tint = EsenlerBlue, modifier = Modifier.size(20.dp))
                    }

                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .background(EsenlerOrange.copy(alpha = 0.1f))
                                    .border(1.dp, EsenlerOrange.copy(alpha = 0.5f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(50.dp), tint = EsenlerOrange)
                            }
                            Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(24.dp).background(Color.White, CircleShape))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "${user.name} ${user.surname}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VerifiedUser, null, tint = Color(0xFF4CAF50), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (strings.language == "App Language") "Verified Youth Profile" else "Doğrulanmış Gençlik Profili",
                                color = Color(0xFF4CAF50),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            SettingsSectionTitle(if (strings.language == "App Language") "ACCOUNT AND SECURITY" else "HESAP VE GÜVENLİK")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    SettingsItem(Icons.Default.Lock, if (strings.language == "App Language") "Password and Security" else "Şifre ve Güvenlik", if (strings.language == "App Language") "Change password, 2FA" else "Şifre değiştir, iki adımlı doğrulama")
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    SettingsItem(Icons.Default.VisibilityOff, if (strings.language == "App Language") "Privacy and Visibility" else "Gizlilik ve Görünürlük", if (strings.language == "App Language") "Who can view my profile?" else "Profilimi kimler görüntüleyebilir?")
                }
            }

            SettingsSectionTitle(if (strings.language == "App Language") "EXPERIENCE AND ACHIEVEMENTS" else "DENEYİM VE KAZANIMLAR")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    SettingsItem(Icons.Default.Star, if (strings.language == "App Language") "My Success Points" else "Esenlik Puanlarım", if (strings.language == "App Language") "Review your current balance" else "Mevcut puan bakiyenizi inceleyin", badgeText = "${user.points} ${if (strings.language == "App Language") "Points" else "Puan"}")
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    SettingsItem(Icons.Default.CardGiftcard, if (strings.language == "App Language") "Reward Catalog / Market" else "Ödül Kataloğu / Market", if (strings.language == "App Language") "Discover rewards" else "Belediye & GSB ödüllerini keşfet")
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    SettingsItem(Icons.Default.HourglassEmpty, if (strings.language == "App Language") "Weekly Mentoring Hours" else "Haftalık Mentörlük Saati", if (strings.language == "App Language") "Your remaining quota" else "Bu haftaki kalan kontenjanınız", trailingText = "2 / 3")
                }
            }

            SettingsSectionTitle(if (strings.language == "App Language") "SYSTEM AND SUPPORT" else "SİSTEM VE DESTEK")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    SettingsItem(Icons.Default.SupportAgent, if (strings.language == "App Language") "Support and FAQ" else "Destek ve SSS", if (strings.language == "App Language") "24/7 support line" else "7/24 Belediye destek hattı ve sorular")
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    SettingsItem(Icons.Default.Description, if (strings.language == "App Language") "Legal Texts" else "KVKK ve Yasal Metinler", if (strings.language == "App Language") "Privacy policy and terms" else "Aydınlatma metni ve kullanıcı sözleşmesi")
                }
            }
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, bottom = 8.dp),
        color = EsenlerGray.copy(alpha = 0.7f),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String, badgeText: String? = null, trailingText: String? = null) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Surface(modifier = Modifier.size(40.dp), shape = RoundedCornerShape(10.dp), color = EsenlerBlue.copy(alpha = 0.05f)) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(10.dp), tint = EsenlerBlue)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
            Text(subtitle, color = EsenlerGray.copy(alpha = 0.6f), fontSize = 11.sp)
        }
        if (badgeText != null) {
            Surface(color = EsenlerOrange, shape = RoundedCornerShape(12.dp)) {
                Text(text = badgeText, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        if (trailingText != null) {
            Text(text = trailingText, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 8.dp))
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.LightGray, modifier = Modifier.size(20.dp))
    }
}
