package com.example.aklatopia.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue

@Composable
fun RatingsDisplay(
    ratings: Double,
    totalRatings: Int,
    totalReviews: Int
){
    Box(
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
            .height(180.dp)
    ){
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 20.dp)
                .width(300.dp)
                .align(Alignment.TopCenter)
        ) {
            ExtraBoldText(
                text = "Ratings",
                size = 24.sp,
                color = DarkBlue,
                modifier = Modifier
                    .padding(10.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                LazyRow {
                    items(5){index ->
                        if(index < ratings.toInt()){
                            Star()
                        }else{
                            EmptyStar()
                        }

                    }
                }
                ExtraBoldText(
                    text = ratings.toString(),
                    size = 24.sp,
                    color = DarkBlue,
                    modifier = Modifier
                )
            }

            Text(
                text = "$totalRatings " +
                        if (totalRatings == 0 || totalRatings == 1) "Rating" + " - $totalReviews " +
                                if (totalReviews == 0 || totalReviews == 1) "Review" else "Reviews"
                        else "Ratings" + " - $totalReviews " +
                                if (totalReviews == 0 || totalReviews == 1) "Review" else "Reviews",
                color = DarkBlue,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(10.dp)

            )
        }
    }
}