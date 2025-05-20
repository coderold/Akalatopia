package com.example.aklatopia.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.R
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue

@Composable
fun SupabaseImageCard(
    pic: String,
    desc: String,
    title: String,
    //navHostController: NavHostController
){
    val windowInfo = rememberWindowInfo()
    Card(
        modifier = Modifier
            .width(windowInfo.screenWidth/2 - 15.dp)
            .padding(10.dp),
            //.clickable { navHostController.navigate("detail/$title") },

        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ){
        Box(
            modifier = Modifier
                .height(250.dp)
        ){
            OnlineImage(
                imageUrl = pic,
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