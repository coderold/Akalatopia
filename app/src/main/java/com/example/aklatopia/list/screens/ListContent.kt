package com.example.aklatopia.list.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.data.BooklistVM
import com.example.aklatopia.data.ListVM
import com.example.aklatopia.home.components.Bookz
import com.example.aklatopia.list.components.ListBookCard
import com.example.aklatopia.list.components.ListContentHeader
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListContentScreen(
    navHostController: NavHostController,
    id: String,
    booklistVM: BooklistVM

){
    val listVM: ListVM = viewModel()
    val listName = listVM.list.find { it.id == id }?.name ?: "Unnamed"

    val booksId = remember { mutableStateListOf<Int>() }

    LaunchedEffect(booklistVM.booklist) {
        booksId.clear()
        booksId.addAll(booklistVM.booklist.filter { it.listId == id }.map { it.bookId })
    }

    ListContentHeader(
        navHostController,
        label = listName
    ){ paddingValues ->
        ListContent(id, navHostController, booklistVM, booksId, paddingValues)
    }

}

@Composable
fun ListContent(
    id: String,
    navHostController: NavHostController,
    booklistVM: BooklistVM,
    booksId: SnapshotStateList<Int>,
    paddingValues: PaddingValues
){

    val SupabaseBooks = remember { mutableStateListOf<Bookz>() }

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            val result = SupabaseClient.client.from("Books").select().decodeList<Bookz>()
            SupabaseBooks.addAll(result)
        }
    }
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    Scaffold (
        floatingActionButton = {
            if (!isScreenRotated){
                FloatingActionButton(
                    onClick = { navHostController.navigate("addToList/$id")},
                    containerColor = Green,
                    contentColor = DarkBlue,
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .size(80.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add",
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .background(Beige)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(padding)
        ){

            val booksInList = SupabaseBooks.filter { it.id in booksId }

            if (booksInList.isNotEmpty()) {
                items(booksInList) { book ->
                    ListBookCard(
                        book.id,
                        label = "Remove From List",
                        navHostController,
                        onClick = {
                            booklistVM.removeFromBooklistByBookId(book.id, id)
                            booksId.remove(book.id)
                        },
                    )
                }
            }else{
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Add books to your list!",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 30.dp)
                        )
                    }
                }
            }
        }
    }
}