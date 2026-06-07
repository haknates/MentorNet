package com.example.myapplication.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.*

@Composable
fun MainNavigation(viewModel: MentorNetViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, 
        startDestination = "home",
        enterTransition = { slideInHorizontally(animationSpec = tween(500), initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { -it }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(animationSpec = tween(500), initialOffsetX = { -it }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(animationSpec = tween(500), targetOffsetX = { it }) + fadeOut() }
    ) {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("settings") { SettingsScreen(navController, viewModel) }
        composable("app_settings") { AppSettingsScreen(navController, viewModel) }
        composable("profile") { ProfileScreen(navController, viewModel) }
        composable("matches") { MatchScreen(navController, viewModel) }
        composable("projects") { ProjectScreen(navController, viewModel) }
        composable("events") { EventScreen(navController, viewModel) }
        composable("friends") { ConnectionListScreen(navController, "Network", viewModel) }
        composable("mentors_list") { ConnectionListScreen(navController, "Mentörüm", viewModel) }
        composable("messages") { MessagesScreen(navController, viewModel) }
        composable("safety_info") { SafetyInfoScreen(navController) }
        composable("ai_matching") { AiMatchingScreen(navController, viewModel) }
        composable(
            "user_detail/{userId}/{userType}?isConnection={isConnection}",
            enterTransition = { scaleIn(initialScale = 0.9f) + fadeIn() }
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val userType = backStackEntry.arguments?.getString("userType") ?: ""
            val isConnection = backStackEntry.arguments?.getString("isConnection")?.toBoolean() ?: false
            UserProfileDetailScreen(navController, viewModel, userId, userType, isConnection)
        }
        composable(
            "ai_results/{type}/{sector}",
            enterTransition = { scaleIn(initialScale = 0.9f) + fadeIn() },
            exitTransition = { scaleOut(targetScale = 1.1f) + fadeOut() }
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: ""
            val sector = backStackEntry.arguments?.getString("sector") ?: ""
            AiResultScreen(navController, viewModel, type, sector)
        }
    }
}
