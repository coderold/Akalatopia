package com.example.aklatopia.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.*

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf("home", "list", "favorites", "profile")
    val icons = listOf(
        Icons.Default.Home,
        Icons.AutoMirrored.Filled.List,
        Icons.Default.FavoriteBorder,
        Icons.Default.Person
    )
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    val selectedItem = when {
        currentRoute.startsWith("home") -> "home"
        currentRoute.startsWith("list") -> "list"
        currentRoute.startsWith("addToList") -> "list"
        currentRoute.startsWith("favorites") -> "favorites"
        currentRoute.contains("profile") || currentRoute.contains("progress") -> "profile"
        else -> "home"
    }
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenHeightInfo is WindowInfo.WindowType.Medium

    NavigationBar(
        containerColor = DarkBlue,
        tonalElevation = 4.dp,
        modifier = Modifier
            .height(if (isScreenRotated) 56.dp else 80.dp)
            .background(Beige)
            .clip(RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp)
            )
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = {
                    navController.navigate(item) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true // This clears the stack
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = icons[index], contentDescription = item)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkBlue,
                    unselectedIconColor = Beige,
                    indicatorColor = Green
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prev(){
    val nav = rememberNavController()
    BottomNavBar(navController = nav)
}