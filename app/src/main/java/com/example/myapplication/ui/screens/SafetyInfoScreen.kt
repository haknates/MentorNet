package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyInfoScreen(navController: NavController) {
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
                        "GÜVENLİK",
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
            Text(
                text = "MENTORNET Güvenlik İlkeleri",
                style = MaterialTheme.typography.headlineSmall,
                color = EsenlerBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SafetySection(
                icon = Icons.Default.Shield,
                title = "Uçtan Uca Güvenli Mesajlaşma",
                description = "Platformumuzdaki tüm mesajlaşmalar güvenlik protokolleri ile korunmaktadır. Verileriniz, topluluk güvenliğini sağlamak ve olası olumsuz durumları önlemek amacıyla sistemlerimizde güvenli bir şekilde saklanmaktadır."
            )

            SafetySection(
                icon = Icons.Default.VerifiedUser,
                title = "Doğrulanmış Kullanıcılar",
                description = "MENTORNET üzerindeki tüm bireyler; TC Kimlik numarası, adli sicil kaydı ve öğrenci/diploma belgeleriyle belediyemiz tarafından doğrulanmış, güvenilir kişilerden oluşmaktadır."
            )

            SafetySection(
                icon = Icons.Default.PrivacyTip,
                title = "Nitelikli Mentör Kadrosu",
                description = "Mentörlerimiz, alanında uzman mühendisler, akademisyenler ve profesyoneller arasından özenle seçilmiştir. Gençlerimize yön gösterecek en yetkin isimlerle çalışmaya özen gösteriyoruz."
            )

            SafetySection(
                icon = Icons.Default.Gavel,
                title = "Sohbet Kuralları ve Değerlerimiz",
                description = "İletişimlerimizde karşılıklı saygı, etik değerler ve toplumsal ahlak kuralları esastır. Hakaret, argo veya topluluk huzurunu bozan davranışlar sergileyen kullanıcılar sistemden kalıcı olarak uzaklaştırılır."
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EsenlerBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Anladım", color = Color.White)
            }
        }
    }
}

@Composable
fun SafetySection(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = EsenlerBlue, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = EsenlerGray, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(description, color = EsenlerGray.copy(alpha = 0.8f), fontSize = 14.sp, lineHeight = 20.sp)
            }
        }
    }
}
