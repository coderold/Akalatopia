package com.example.aklatopia.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.R
import com.example.aklatopia.data.Review
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Yellow

@Composable
fun ReviewCard(review: Review){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .background(Yellow)
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ){
                OnlineImage(
                    imageUrl = review.profilePic,
                    contentDescription = "ProfilePic",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = review.name,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 16.sp,
                        color = DarkBlue
                    )
                    Text(
                        text = review.date,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp,
                        color = DarkBlue
                    )
                }
            }

            Text(
                text = review.review,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = DarkBlue,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }

    }
}