package com.example.aklatopia.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.aklatopia.R
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.auth.components.RoutedButton
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue


@Composable
fun GetStarted(navHostController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue)
    ){
        val windowInfo = rememberWindowInfo()
        val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium
        Box(modifier = Modifier
            .fillMaxWidth(1f)
            .clip(
                if(isScreenRotated) RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)
                else RoundedCornerShape(topStart = 180.dp, topEnd = 180.dp)
            )
            .background(color = Beige)
            .fillMaxHeight(0.6f)
            .align(Alignment.BottomCenter)
        )
        Image(
            painter = painterResource(id = R.drawable.get_started_book),
            contentDescription = "book",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 200.dp)
                .size(440.dp)

        )

        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
                .padding(20.dp),
            horizontalAlignment = if(isScreenRotated) Alignment.CenterHorizontally else Alignment.Start
        ){
            Text(
                text = "Aklatopia",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = if(isScreenRotated) 38.sp else 48.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Helping parents select stories that educate, " +
                        "entertain, and support their child's growth.",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = DarkBlue,
                fontSize = if(isScreenRotated) 12.sp else 16.sp
            )

            RoutedButton(
                label = "Get Started",
                navHostController = navHostController,
                route = "login",
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(if(isScreenRotated) 50.dp else 70.dp)
            )

        }
    }
}