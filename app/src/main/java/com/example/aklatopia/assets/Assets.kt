package com.example.aklatopia.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aklatopia.R
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue

@Composable
fun Header(content: @Composable (PaddingValues) -> Unit){
    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .background(Beige)
                .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .background(DarkBlue)
                .fillMaxWidth()
                .fillMaxHeight(0.12f)
            )
            {
                Text(
                    text = "Aklatopia",
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    color = Beige,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp, bottom = 10.dp)
                )

            }

        }
    ){
            padding ->
        content(padding)
    }
}

@Composable
fun HeaderWithBack(
    navHostController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .background(Beige)
                .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .background(DarkBlue)
                .fillMaxWidth()
                .fillMaxHeight(0.12f)
            )
            {
                BeigeBackButton(
                    navHostController = navHostController,
                    modifier = Modifier
                        .padding(top = 22.dp, start = 10.dp)
                )
                Text(
                    text = "Aklatopia",
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    color = Beige,
                    fontSize = 36.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp, bottom = 10.dp)
                )

            }

        }
    ){
            padding ->
        content(padding)
    }
}

@Composable
fun ImageCard(
    pic: Int,
    desc: String,
    title: String,
    navHostController: NavHostController
){
    val windowInfo = rememberWindowInfo()
    Card(
        modifier = Modifier
            .width(windowInfo.screenWidth/2 - 15.dp)
            .padding(10.dp)
            .clickable { navHostController.navigate("detail/$title") },

        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ){
        Box(
            modifier = Modifier
                .height(250.dp)
        ){
            Image(
                painter = painterResource(id = pic),
                contentDescription = desc,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            DarkBlue
                        ),
                        startY = 300f
                    )
                ))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.BottomStart

            ){
                Text(
                    text = title,
                    color = Beige,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                )
            }
        }
    }
}

@Composable
fun ExtraBoldText(text: String, size: TextUnit, color: Color, modifier: Modifier){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        fontSize = size,
        modifier = modifier
    )
}

@Composable
fun Line(){
    Box(
        modifier = Modifier
            .background(DarkBlue)
            .fillMaxWidth()
            .height(1.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun AssetsPrev(){
//    val nav = rememberNavController()
//    ListHeader(label = "List") {
//
//    }
}