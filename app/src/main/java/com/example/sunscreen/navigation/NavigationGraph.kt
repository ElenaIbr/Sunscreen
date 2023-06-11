package com.example.sunscreen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sunscreen.ui.main.MainScreen
import com.example.sunscreen.ui.notifications.NotificationScreen
import com.example.sunscreen.ui.profile.ProfileScreen
import com.example.sunscreen.ui.questionnaire.QuestionnaireScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Questionnaire.screenRoute) {
        composable(BottomNavItem.UvIndex.screenRoute) {
            MainScreen(navController = navController)
        }
        composable(BottomNavItem.Profile.screenRoute) {
            ProfileScreen(navController = navController)
        }
        composable(BottomNavItem.Notifications.screenRoute) {
            NotificationScreen(navController = navController)
        }
        composable(BottomNavItem.Questionnaire.screenRoute) {
            QuestionnaireScreen {
                navController.navigate(BottomNavItem.UvIndex.screenRoute)
            }
        }
    }
}
