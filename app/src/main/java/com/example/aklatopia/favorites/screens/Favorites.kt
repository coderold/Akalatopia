package com.example.aklatopia.favorites.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.assets.ImageCard
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.data.favorites
import com.example.aklatopia.assets.LabeledHeader
import com.example.aklatopia.list.components.ListBookCard
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige

@Composable
fun FavoritesScreen(navHostController: NavHostController){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    LabeledHeader(label = "Favorites") { padding->
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isScreenRotated) 1 else 2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(Beige)
                .fillMaxSize()
                .padding(padding)
        ){
            if (isScreenRotated){

                items(favorites){ book->
                    ListBookCard(
                        book.title,
                        label = "Remove From Favorites",
                        navHostController,
                        onClick = {},
                    )
                }

            } else {

                items(favorites){ book->
                    ImageCard(
                        pic = book.cover,
                        desc = book.desc,
                        title = book.title,
                        navHostController = navHostController
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavPrev(){
    val nav = rememberNavController()
    FavoritesScreen(nav)
}