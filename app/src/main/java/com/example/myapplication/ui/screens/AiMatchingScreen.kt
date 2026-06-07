package com.example.myapplication.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.MentorNetViewModel
import com.example.myapplication.ui.getAppStrings
import com.example.myapplication.ui.theme.EsenlerBlue
import com.example.myapplication.ui.theme.EsenlerOrange
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiMatchingScreen(navController: NavController, viewModel: MentorNetViewModel) {
    val strings = getAppStrings(viewModel)
    val isEnglish by viewModel.isEnglish.collectAsState()
    
    var step by remember { mutableStateOf(1) }
    var selectedType by remember { mutableStateOf("") }
    var selectedSector by remember { mutableStateOf("") }
    var selectedReason by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisText by remember { mutableStateOf("") }

    LaunchedEffect(isAnalyzing) {
        if (isAnalyzing) {
            analysisText = strings.analyzeInterests
            delay(2000)
            analysisText = if (selectedType == (if (isEnglish) "Mentor" else "Mentör")) {
                strings.findingMentor
            } else {
                if (isEnglish) "Finding suitable friends for you." else "Sizin için uygun arkadaşlar bulunuyor."
            }
            delay(1500)
            navController.navigate("ai_results/$selectedType/$selectedSector") {
                popUpTo("home")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.aiMatching, color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = EsenlerBlue),
                navigationIcon = {
                    IconButton(onClick = { 
                        if (step > 1) step-- else navController.popBackStack() 
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, strings.back, tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            if (isAnalyzing) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = EsenlerOrange, strokeWidth = 4.dp)
                    Spacer(modifier = Modifier.height(24.dp))
                    AnimatedContent(
                        targetState = analysisText,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
                        },
                        label = "TextAnim"
                    ) { targetText ->
                        Text(
                            text = targetText,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = EsenlerBlue,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = EsenlerOrange
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    AnimatedContent(
                        targetState = step,
                        transitionSpec = {
                            if (targetState > initialState) {
                                (slideInHorizontally { it } + fadeIn()) togetherWith (slideOutHorizontally { -it } + fadeOut())
                            } else {
                                (slideInHorizontally { -it } + fadeIn()) togetherWith (slideOutHorizontally { it } + fadeOut())
                            }.using(SizeTransform(clip = false))
                        },
                        label = "StepAnimation"
                    ) { currentStep ->
                        when (currentStep) {
                            1 -> QuestionView(
                                if (isEnglish) "Are you looking for a mentor or a friend?" else "Mentör mü arıyorsun arkadaş mı?",
                                if (isEnglish) listOf("Mentor", "Friend") else listOf("Mentör", "Arkadaş")
                            ) {
                                selectedType = it
                                step = 2
                            }
                            2 -> {
                                val question = if (selectedType == (if (isEnglish) "Mentor" else "Mentör")) 
                                    (if (isEnglish) "In which sector do you want a mentor?" else "Hangi sektörde çalışan bir mentör istersin?") 
                                    else (if (isEnglish) "What areas are you interested in?" else "Hangi alanlara ilgi duyuyorsun?")
                                
                                val options = if (isEnglish) 
                                    listOf("Software", "Design", "Entrepreneurship", "Finance", "Health", "Education")
                                    else listOf("Yazılım", "Tasarım", "Girişimcilik", "Finans", "Sağlık", "Eğitim")
                                    
                                QuestionView(question, options) {
                                    selectedSector = it
                                    step = 3
                                }
                            }
                            3 -> {
                                val question = if (selectedType == (if (isEnglish) "Mentor" else "Mentör")) 
                                    (if (isEnglish) "Why do you want mentoring?" else "Ne için mentörlük istiyorsun?") 
                                    else (if (isEnglish) "What is your main reason for making friends?" else "Arkadaş edinme isteğinizin temel sebebi nedir?")
                                
                                val options = if (selectedType == (if (isEnglish) "Mentor" else "Mentör")) {
                                    if (isEnglish) listOf("Guide my career", "Get info about sector", "Consult for my project", "Other")
                                    else listOf("Kariyerimi yönlendirmek", "Sektör hakkında bilgi edinmek", "Projem için danışmanlık almak", "Diğer")
                                } else {
                                    if (isEnglish) listOf("Grow my network", "Find team for project", "Join municipality events", "Other")
                                    else listOf("Networkümü geliştirmek", "Projeme takım arkadaşları bulmak", "Belediye etkinliklerine katılmak", "Diğer")
                                }
                                QuestionView(question, options) {
                                    selectedReason = it
                                    isAnalyzing = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionView(question: String, options: List<String>, onOptionSelected: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = question,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(option, color = EsenlerBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
