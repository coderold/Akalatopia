package com.example.aklatopia.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.assets.ImageCard
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.assets.SupabaseImageCard
import com.example.aklatopia.data.books
import com.example.aklatopia.home.components.Bookz
import com.example.aklatopia.home.components.HomeBookCard
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.*
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Home(navHostController: NavHostController){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    val SupabaseBooks = remember { mutableStateListOf<Bookz>() }

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            val result = SupabaseClient.client.from("Books").select().decodeList<Bookz>()
            SupabaseBooks.addAll(result)
        }
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .background(Beige)
                .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                .background(DarkBlue)
                .fillMaxWidth()
                .fillMaxHeight(0.23f)
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
                        .alpha(if (isScreenRotated) 0f else 1f)
                )
                SearchBarBtn(
                    navHostController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    isScreenRotated
                )
            }

        }
    ){
        padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(Beige)
                .fillMaxSize()
        ) {
            if (isScreenRotated){
                LazyColumn{
                    items(books){ book->
                        HomeBookCard(
                            book.title,
                            label = "Add to List",
                            navHostController,
                            onClick = {}
                        )
                    }
                }
            } else{
                BookGrid(navHostController)

//                if (SupabaseBooks.isEmpty()) {
//                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        CircularProgressIndicator(color = DarkBlue)
//                    }
//                }
//
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    verticalArrangement = Arrangement.spacedBy(10.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ){
//
//                    items(SupabaseBooks){ book->
//                        SupabaseImageCard(
//                            pic = book.cover,
//                            desc = book.desc,
//                            title = book.title,
//                            id = book.id,
//                            navHostController = navHostController
//                        )
//                    }
//                }

            }

        }
    }
}

@Composable
fun SearchBarBtn(navHostController: NavHostController, modifier: Modifier, isScreenRotated: Boolean){
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(if (isScreenRotated) 10.dp else 20.dp)
                .heightIn(min = 55.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(OffWhite)
                .clickable { navHostController.navigate("search") }
        )
    ){
        Text(
            text = "Search...",
            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
            color = DarkBlue,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
        )
        Icon(
            Icons.Default.Search,
            contentDescription = "Search",
            tint = DarkBlue,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 15.dp)
        )
    }
}

@Composable
fun BookGrid(navHostController: NavHostController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        items(books){ book->
            ImageCard(
                pic = book.cover,
                desc = book.desc,
                title = book.title,
                navHostController = navHostController
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomePrev(){
    val navHostController = rememberNavController()
    Home(navHostController)
    val book = books[0]
    //Reviews(book.review)
}
