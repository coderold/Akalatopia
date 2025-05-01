package com.example.aklatopia.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aklatopia.assets.BeigeBackButton
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue

@Composable
fun DetailHeader(
    navHostController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .background(Beige)
                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                .background(DarkBlue)
                .fillMaxWidth()
                .fillMaxHeight(if (isScreenRotated) 0.2f else 0.12f)
            )
            {
                BeigeBackButton(
                    navHostController = navHostController,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "Aklatopia",
                    style = MaterialTheme.typography.titleLarge,
                    color = Beige,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )

            }

        }
    ){
            padding ->
        content(padding)
    }
}