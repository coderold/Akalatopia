package com.example.aklatopia.home.components

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.data.User
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeBookCard(
    title: String,
    label: String,
    navHostController: NavHostController,
    onClick: () -> Unit
){

    val SupabaseBooks = remember { mutableStateListOf<Bookz>() }

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            val result = SupabaseClient.client.from("Books").select().decodeList<Bookz>()
            SupabaseBooks.addAll(result)
        }
    }
//    val book = books[books.indexOfFirst { it.title == title }]
    val book = SupabaseBooks[SupabaseBooks.indexOfFirst { it.title == title }]
    var expanded by remember { mutableStateOf(false) }
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
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ){
            Row{
                OnlineImage(
                    imageUrl = book.cover,
                    contentDescription = book.desc,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxHeight()
                        .padding(5.dp)
                        .requiredWidth(100.dp)
                )
                Text(
                    text = book.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .fillMaxWidth(0.7f)
                        .align(Alignment.CenterVertically)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = DarkBlue,
                            modifier = Modifier
                                .size(300.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Beige)
                            .align(Alignment.TopCenter)
                    ) {
                        DropdownMenuItem(
                            text = { Text(
                                text = label,
                                color = DarkBlue,
                                fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                            ) },
                            onClick = {
                                expanded = false
                                onClick()
                            }
                        )
                    }
                }
            }
        }

    }
}