package com.example.aklatopia.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.assets.Line
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.OffWhite

@Composable
fun ReviewDialog(
    onDismiss: () -> Unit,
    onPost: (String) -> Unit,
    username: String,
    handle: String,
    profilePic: Int
) {
    var reviewText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Beige)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onDismiss() }
                    .size(40.dp)
                    .padding(start = 5.dp)
            )
            Text(
                text = "Leave a Review!",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                fontSize = 20.sp,
                color = DarkBlue,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(scrollState)
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = profilePic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.Gray)
                        .height(48.dp)
                        .requiredWidth(48.dp)
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = username, fontWeight = FontWeight.Bold, color = DarkBlue)
                    Text(text = handle, fontSize = 12.sp, color = DarkBlue)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OffWhite),
                placeholder = { Text("Write your review...") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = OffWhite,
                    focusedContainerColor = OffWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onPost(reviewText) },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Post", color = Beige)
            }
        }
    }
}