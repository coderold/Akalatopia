package com.example.aklatopia.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.Red

@Composable
fun RateDialog(
    onDismiss: () -> Unit,
    onRate: (Int) -> Unit
) {
    var rating by remember { mutableStateOf(0) }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Beige
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rate This Book!",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                fontSize = 20.sp,
                color = DarkBlue,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    Box(modifier = Modifier.clickable {
                        rating = i
                    }) {
                        if (i <= rating) {
                            Star()
                        } else {
                            EmptyStar()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Red),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text(
                        text = "Cancel",
                        color = Beige,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { onRate(rating) },
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text(
                        text = "Rate",
                        color = Beige,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))
                    )
                }
            }
        }
    }
}