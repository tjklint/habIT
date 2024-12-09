package com.example.habitai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habitai.ui.theme.HabITAITheme
import com.google.android.gms.tasks.Task

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabITAITheme {
                val navController = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(navController, startDestination = "login_screen") {
                        composable("login_screen") {
                            LoginScreen()
                        }
                        composable("register_screen") {
                            RegisterScreen()
                        }
                        composable("home_screen") {
                            HomeScreen()
                        }
                        composable("profile_screen"){
                            ProfileScreen()
                        }
                        composable("task_screen"){
                            TaskScreen()
                        }
                        composable("taskmanager_screen"){
                            TaskManagerScreen()
                        }
                        composable("calendar_screen"){
                            CalendarScreen()
                        }
                        composable("taskcheck_screen"){
                            TaskCheckScreen()
                        }


                    }
                }
            }
        }
    }
}