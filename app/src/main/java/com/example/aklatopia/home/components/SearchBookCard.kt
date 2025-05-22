package com.example.aklatopia.home.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aklatopia.R
import com.example.aklatopia.data.books
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Yellow

@Composable
fun SearchBookCard(
    title: String,
    navHostController: NavHostController,
    onClick: () -> Unit
){
    val book = books[books.indexOfFirst { it.title == title }]

    Card (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable { navHostController.navigate("detail/$title") },
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Yellow)
        ){
            Row{
                Image(
                    painter = painterResource(id = book.cover),
                    contentDescription = book.desc,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(OffWhite)
                        .fillMaxHeight()
                        .padding(5.dp)
                        .requiredWidth(100.dp)
                )
                Text(
                    text = book.title,
                    color = DarkBlue,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterVertically)
                )
            }
        }

    }
}