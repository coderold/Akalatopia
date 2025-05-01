package com.example.aklatopia.profile.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.data.BookCategory
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.assets.LabeledHeaderWithBackBtn
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.Yellow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProgressScreen(
    navHostController: NavHostController,
){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium
    LabeledHeaderWithBackBtn(
        navHostController,
        label = "Progress"
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier
                .padding(paddingValues)
                .background(Beige)
        ) {
            stickyHeader {
                ProfileCard(
                    profilePic = R.drawable.user_profile_pic,
                    name = "Matthew Molina",
                    userName = "@pusangpagod",
                    isScreenRotated = isScreenRotated
                )
                Line()
            }
            items(BookCategory.allCategories){ bookCategory->
                ProgressBar(
                    category = bookCategory.displayName,
                    progress = bookCategory.progress
                )
            }

        }
    }
}

@Composable
fun ProfileCard(
    profilePic: Int,
    name: String,
    userName: String,
    isScreenRotated: Boolean
){
    Box(
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.Center)
        ) {
            Card (
                shape = RoundedCornerShape(75.dp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(if (isScreenRotated) 40.dp else 60.dp)
            ) {
                Image(
                    painter = painterResource(id = profilePic),
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ){
                Text(
                    text = name,
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    fontSize = if (isScreenRotated) 18.sp else 24.sp,
                    color = DarkBlue
                )
                Text(
                    text = userName,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    fontSize = if (isScreenRotated) 12.sp else 15.sp,
                    color = DarkBlue
                )
            }
        }
    }
}

@Composable
fun ProgressBar(
    category: String,
    progress: Int
){
    Box(
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
            .height(130.dp)
    ){
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Yellow)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    ExtraBoldText(
                        text = category,
                        size = 18.sp,
                        color = DarkBlue,
                        modifier = Modifier
                    )
                    ExtraBoldText(
                        text = "$progress%",
                        size = 18.sp,
                        color = DarkBlue,
                        modifier = Modifier
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(progress.toFloat() / 100)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Green)
                        .align(Alignment.BottomStart)
                )

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgPrev(){
    val nav = rememberNavController()
    ProgressScreen(nav)
}