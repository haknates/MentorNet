package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val scrollState = rememberScrollState()
    val strings = getAppStrings(viewModel)
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isEnglish by viewModel.isEnglish.collectAsState()

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
                        strings.appSettings,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
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
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            SettingsGroupTitle(if (isEnglish) "APPEARANCE AND ACCESSIBILITY" else "GÖRÜNÜM VE ERİŞİLEBİLİRLİK")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    SettingsSwitchItem(Icons.Default.DarkMode, strings.darkMode, if (isEnglish) "Dark theme for night use" else "Gece kullanımı için koyu tema", isDarkMode) { 
                        viewModel.toggleDarkMode(it) 
                    }
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    
                    var accessibilityMode by remember { mutableStateOf(true) }
                    SettingsSwitchItem(Icons.Default.AccessibilityNew, strings.accessibilityMode, if (isEnglish) "Dyslexia font and large size" else "Disleksi yazı tipi ve büyük punto", accessibilityMode) { accessibilityMode = it }
                    
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SettingsIconBox(Icons.Default.Translate)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(strings.language, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                                Text(if (isEnglish) "Switch to Turkish" else "İngilizceye geç", color = EsenlerGray.copy(alpha = 0.6f), fontSize = 11.sp)
                            }
                        }
                        Switch(
                            checked = isEnglish,
                            onCheckedChange = { viewModel.setLanguage(it) },
                            colors = SwitchDefaults.colors(checkedTrackColor = EsenlerBlue)
                        )
                    }
                }
            }

            SettingsGroupTitle(if (isEnglish) "NOTIFICATIONS AND MATCHES" else "BİLDİRİM VE EŞLEŞMELER")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    var notifications by remember { mutableStateOf(true) }
                    var examMode by remember { mutableStateOf(false) }

                    SettingsSwitchItem(Icons.Default.Notifications, strings.notifications, if (isEnglish) "Match and message alerts" else "Eşleşme ve mesaj bildirimleri", notifications) { notifications = it }
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    SettingsSwitchItem(Icons.Default.School, strings.examMode, if (isEnglish) "Temporarily freeze matches" else "Eşleşmeleri geçici olarak dondur", examMode) { examMode = it }
                }
            }

            SettingsGroupTitle(if (isEnglish) "STORAGE AND PERFORMANCE" else "DEPOLAMA VE PERFORMANS")
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    var dataSaver by remember { mutableStateOf(true) }

                    SettingsNavigationItem(Icons.Default.Delete, strings.clearCache, if (isEnglish) "Reset image and chat data" else "Görsel ve sohbet verilerini sıfırla", "24.5 MB")
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.3f))
                    SettingsSwitchItem(Icons.Default.FlashOn, strings.dataSaver, if (isEnglish) "Load images only on Wi-Fi" else "Görselleri sadece Wi-Fi ile yükle", dataSaver) { dataSaver = it }
                }
            }
        }
    }
}

@Composable
fun SettingsGroupTitle(title: String) {
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
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIconBox(icon)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
            Text(subtitle, color = EsenlerGray.copy(alpha = 0.6f), fontSize = 11.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = EsenlerBlue,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    trailingText: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIconBox(icon)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
            Text(subtitle, color = EsenlerGray.copy(alpha = 0.6f), fontSize = 11.sp)
        }
        if (trailingText != null) {
            Text(
                text = trailingText,
                color = EsenlerGray.copy(alpha = 0.5f),
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun SettingsIconBox(icon: ImageVector) {
    Surface(
        modifier = Modifier.size(40.dp),
        shape = RoundedCornerShape(10.dp),
        color = EsenlerBlue.copy(alpha = 0.1f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(10.dp),
            tint = EsenlerBlue
        )
    }
}
