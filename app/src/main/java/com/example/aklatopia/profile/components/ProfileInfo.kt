package com.example.aklatopia.profile.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.R
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Yellow

@Composable
fun ProfileInfo(imageUri: MutableState<Uri?>){
    Box(
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
            .height(300.dp)
    ){
        Card (
            shape = RoundedCornerShape(75.dp),
            modifier = Modifier
                .padding(20.dp)
                .size(100.dp)
                .align(Alignment.TopCenter)
        ) {

            if (imageUri.value != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri.value),
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                OnlineImage(
                    imageUrl = SupabaseUser.userState.value.avatar,
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 125.dp, start = 20.dp)
        ) {
            Text(
                text = SupabaseUser.userState.value.name,
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                fontSize = 24.sp,
                color = DarkBlue
            )
            Text(
                text = "@" + SupabaseUser.userState.value.userName,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontSize = 15.sp,
                color = DarkBlue
            )
        }

        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Yellow)
                .align(Alignment.BottomCenter)

        ){
            Text(
                text = SupabaseUser.userState.value.bio,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = DarkBlue,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }


}