package com.example.aklatopia.favorites.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.FBFavBooks
import com.example.aklatopia.SupabaseBooksData
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.assets.ImageCard
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.assets.FavoriteImageCard
import com.example.aklatopia.assets.LabeledHeader
import com.example.aklatopia.assets.SupabaseImageCard
import com.example.aklatopia.data.FavoritesVM
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.home.components.Bookz
import com.example.aklatopia.list.components.ListBookCard
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun FavoritesScreen(
    navHostController: NavHostController,
    favoritesVM: FavoritesVM
){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    //val favoritesId = remember { mutableStateListOf<Int>() }
    val favoritesLoaded = remember { mutableStateOf(false) }

    val allBooks =  SupabaseBooksData.booksState

    LaunchedEffect(favoritesVM.favorites){
        FBFavBooks.id.clear()
        FBFavBooks.id.addAll(favoritesVM.favorites
            .filter { it.userId == SupabaseUser.userState.value.userId }
            .map { it.bookId })
        favoritesLoaded.value = true
    }

    val favoriteBooks = allBooks.filter { it.id in FBFavBooks.id }

    LabeledHeader(label = "Favorites") { padding->

        if (favoritesLoaded.value){
            if (FBFavBooks.id.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(if (isScreenRotated) 1 else 2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .background(Beige)
                        .fillMaxSize()
                        .padding(padding)
                ){

                    items(favoriteBooks){ book->
                        FavoriteImageCard(
                            pic = book.cover,
                            desc = book.desc,
                            title = book.title,
                            id = book.id,
                            navHostController = navHostController
                        )
                    }

                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ){
                    Text(
                        text = "You have no Favorite Books yet.",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }else{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }

    }
}
