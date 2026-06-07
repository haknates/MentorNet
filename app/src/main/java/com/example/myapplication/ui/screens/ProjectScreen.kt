package com.example.myapplication.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerGray
import com.example.myapplication.ui.theme.EsenlerOrange
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.theme.EsenlerGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(navController: NavController, viewModel: MentorNetViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Projelere Katıl", "Takımımı Kur")
    
    // State for created projects
    var myProjects by remember { mutableStateOf(listOf<ProjectData>()) }
    var showCreateForm by remember { mutableStateOf(false) }
    
    // Sample projects to join
    val availableProjects = listOf(
        ProjectData("Teknofest İHA", "Yarışma", 5, 3, "Python, ROS, Gömülü Sistemler"),
        ProjectData("E-Ticaret Girişimi", "Girişim", 4, 1, "Kotlin, Figma, Pazarlama"),
        ProjectData("Tübitak 2204-A", "Bilimsel", 3, 2, "Veri Analizi, Kimya"),
        ProjectData("Akıllı Şehir Esenler", "Yarışma", 6, 2, "Mobil Geliştirme, IoT")
    )

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
                            "PROJE ORTAĞI BUL",
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
                            TabRowDefaults.SecondaryIndicator(
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
        },
        floatingActionButton = {
            if (selectedTab == 1 && !showCreateForm) {
                FloatingActionButton(
                    onClick = { showCreateForm = true },
                    containerColor = EsenlerOrange,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, "Yeni Takım")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
        ) {
            if (selectedTab == 0) {
                JoinProjectsTab(availableProjects)
            } else {
                if (showCreateForm) {
                    CreateTeamForm(onCancel = { showCreateForm = false }) { newProject ->
                        myProjects = myProjects + newProject
                        showCreateForm = false
                    }
                } else {
                    MyTeamsTab(myProjects)
                }
            }
        }
    }
}

@Composable
fun JoinProjectsTab(projects: List<ProjectData>) {
    var selectedProject by remember { mutableStateOf<ProjectData?>(null) }

    if (selectedProject != null) {
        ProjectDetailDialog(project = selectedProject!!) {
            selectedProject = null
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(projects) { project ->
            ProjectCard(project) {
                selectedProject = project
            }
        }
    }
}

@Composable
fun MyTeamsTab(myProjects: List<ProjectData>) {
    if (myProjects.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.RocketLaunch, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Henüz bir takım kurmadınız.", color = EsenlerGray)
            Text("Alttaki butona basarak ilk ekibinizi kurun!", color = EsenlerGray, fontSize = 12.sp)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("KURDUĞUM TAKIMLAR", style = MaterialTheme.typography.labelLarge, color = EsenlerGray.copy(alpha = 0.7f))
            }
            items(myProjects) { project ->
                ProjectCard(project, isOwn = true) { }
            }
        }
    }
}

@Composable
fun ProjectCard(project: ProjectData, isOwn: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = if (isOwn) EsenlerGreen.copy(alpha = 0.1f) else EsenlerBlue.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        project.type,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = if (isOwn) EsenlerGreen else EsenlerBlue,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Groups, null, modifier = Modifier.size(14.dp), tint = EsenlerGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${project.currentMembers}/${project.totalRequired} Kişi", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(project.name, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Gereksinimler:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = EsenlerGray)
            Text(project.requirements, fontSize = 12.sp, color = EsenlerGray.copy(alpha = 0.8f))
            
            if (!isOwn) {
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { project.currentMembers.toFloat() / project.totalRequired },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = EsenlerOrange,
                    trackColor = EsenlerOrange.copy(alpha = 0.1f)
                )
            }
        }
    }
}

@Composable
fun CreateTeamForm(onCancel: () -> Unit, onCreated: (ProjectData) -> Unit) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Yarışma") }
    var count by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Yeni Proje Takımı Kur", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = EsenlerBlue)
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Proje İsmi") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text("Proje Türü", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Yarışma", "Girişim", "Bilimsel").forEach { option ->
                FilterChip(
                    selected = type == option,
                    onClick = { type = option },
                    label = { Text(option) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = count,
            onValueChange = { count = it },
            label = { Text("Toplam Kaç Kişi Lazım?") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        OutlinedTextField(
            value = skills,
            onValueChange = { skills = it },
            label = { Text("Ne tür nitelikler aranıyor? (örn: Tasarımcı, Kodlayıcı)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                if (name.isNotBlank() && count.isNotEmpty()) {
                    onCreated(ProjectData(name, type, count.toIntOrNull() ?: 1, 1, skills))
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EsenlerOrange)
        ) {
            Text("TAKIMI OLUŞTUR", fontWeight = FontWeight.Bold)
        }
        
        TextButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
            Text("İptal", color = EsenlerGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailDialog(project: ProjectData, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { /* Send Request */ onDismiss() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EsenlerBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("KATILMA İSTEĞİ GÖNDER")
            }
        },
        title = { Text(project.name, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Tür: ${project.type}", color = EsenlerGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Mevcut Durum: ${project.currentMembers}/${project.totalRequired} kişi ekipte.", color = EsenlerGray)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Proje Detayları:", fontWeight = FontWeight.Bold)
                Text("Bu proje Esenler Belediyesi bünyesinde ${project.type} kategorisinde geliştirilmektedir. Aranan özellikler: ${project.requirements}.", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Güvenlik Notu: Esenler Gençlik platformu üzerinden kurulan takımların tüm iletişimleri denetlenmektedir.", fontSize = 11.sp, color = EsenlerOrange)
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White
    )
}

data class ProjectData(
    val name: String,
    val type: String,
    val totalRequired: Int,
    val currentMembers: Int,
    val requirements: String
)
