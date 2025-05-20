package com.example.aklatopia.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.list.screens.AddToList
import com.example.aklatopia.home.screens.DetailScreen
import com.example.aklatopia.favorites.screens.FavoritesScreen
import com.example.aklatopia.home.screens.Home
import com.example.aklatopia.list.screens.ListContentScreen
import com.example.aklatopia.list.screens.ListScreen
import com.example.aklatopia.auth.screens.Login
import com.example.aklatopia.profile.screens.ProfileScreen
import com.example.aklatopia.profile.screens.ProgressScreen
import com.example.aklatopia.home.screens.Search
import com.example.aklatopia.auth.screens.SignUp
import com.example.aklatopia.auth.screens.GetStarted
import com.example.aklatopia.data.FirebaseReviewVM
import com.example.aklatopia.home.components.BookVM

@Composable
fun StartingNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "started",
        enterTransition = {
            fadeIn(animationSpec = tween(200))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(200))
        }

    ) {
        composable("started") {
            GetStarted(navController)
        }
        composable("login") {
            Login(navController)
        }
        composable("signup") {
            SignUp(navController)
        }
        composable("main") {
            MainAppScreen()
        }

    }
}

@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding),
            enterTransition = {
                fadeIn(animationSpec = tween(200))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(200))
            }

        ) {
            composable("home") { Home(navController) }
            composable("list") { ListScreen(navController) }
            composable("favorites") { FavoritesScreen(navController) }
            composable("profile") { ProfileScreen(navController) }

            composable("search") { Search(navController) }
            composable("progress") { ProgressScreen(navController) }

            composable("addToList/{listName}") { backStackEntry ->
                val listName = backStackEntry.arguments?.getString("listName")
                AddToList(navController, listName?: "No info")
            }

            composable("detail/{title}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                DetailScreen(navController, title?: "No info", viewModel = FirebaseReviewVM())
            }

            composable("listContent/{listName}") { backStackEntry ->
                val listName = backStackEntry.arguments?.getString("listName")
                ListContentScreen(navController, listName?: "No info")
            }
        }
    }
}
