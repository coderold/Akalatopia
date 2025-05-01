package com.example.aklatopia.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
fun ListContentHeader(
    navHostController: NavHostController,
    label: String,
    content: @Composable (PaddingValues) -> Unit
){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium
    Scaffold(
        topBar = {
            Column {
                Box(modifier = Modifier
                    .background(Beige)
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(DarkBlue)
                    .fillMaxWidth()
                    .fillMaxHeight(if (isScreenRotated) 0.23f else 0.12f)
                )
                {
                    BeigeBackButton(
                        navHostController = navHostController,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterStart)
                    )
                    Text(
                        text = if (isScreenRotated) label else "Aklatopia",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 36.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                    if (isScreenRotated){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(40.dp)
                                .padding(end = 10.dp)
                                .clickable { navHostController.navigate("addToList/$label") },
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }

                }
                if (!isScreenRotated){
                    Box(
                        modifier = Modifier
                            .background(Beige)
                            .fillMaxWidth()
                    ){
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 32.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(DarkBlue)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }

            }
        }
    ){
            padding ->
        content(padding)
    }
}