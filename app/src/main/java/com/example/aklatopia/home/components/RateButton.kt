package com.example.aklatopia.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Yellow

@Composable
fun RateButton(
    isBookRated: Boolean,
    onClick: () -> Unit
){

    if (!isBookRated){
        Button(
            onClick = onClick,
            modifier = Modifier
                .height(40.dp)
                .width(190.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue
            )
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Yellow,
                modifier = Modifier
                    .size(22.dp)
            )
            Text(
                text = "Rate this Book",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                color = Beige,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
    }else {
        Button(
            onClick = onClick,
            enabled = false,
            modifier = Modifier
                .height(40.dp)
                .width(190.dp),
        ) {
            Text(
                text = "Book Rated",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                color = DarkBlue,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
    }

}