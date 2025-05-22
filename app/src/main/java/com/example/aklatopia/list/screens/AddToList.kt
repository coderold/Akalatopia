package com.example.aklatopia.list.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aklatopia.assets.BeigeBackButton
import com.example.aklatopia.data.BookCategory
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.data.Book
import com.example.aklatopia.data.Booklist
import com.example.aklatopia.data.BooklistVM
import com.example.aklatopia.data.ListVM
import com.example.aklatopia.data.books
import com.example.aklatopia.data.user
import com.example.aklatopia.home.components.Bookz
import com.example.aklatopia.home.screens.CategoriesRow
import com.example.aklatopia.home.screens.CustomShapeSearchBar
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Yellow
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddToList(navHostController: NavHostController, listId: String, booklistVM: BooklistVM){
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<BookCategory?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    val booksId = remember { mutableStateListOf<Int>() }

    LaunchedEffect(booklistVM.booklist) {
        booksId.clear()
        booksId.addAll(booklistVM.booklist.filter { it.listId == listId }.map { it.bookId })
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    containerColor = DarkBlue,
                    contentColor = Beige
                ) {
                    Text(data.visuals.message)
                }
            }
        },
        topBar = {
            Column {
                Box(
                    modifier = Modifier
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    ) {
                        BeigeBackButton(
                            navHostController,
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.CenterStart)
                        )
                        CustomShapeSearchBar(
                            modifier = Modifier
                                .padding(start = 35.dp)
                                .align(Alignment.CenterEnd),
                            text = searchText,
                            onTextChange = { searchText = it },
                            isScreenRotated
                        )
                    }
                }

                if (!isScreenRotated){
                    ExtraBoldText(
                        text = "Categories",
                        size = 24.sp,
                        color = DarkBlue,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                CategoriesRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        coroutineScope.launch {
                            selectedCategory = if (selectedCategory == category) null else category
                        }
                    }
                )
                Line()
            }
        }
    ){
            padding ->

        AddToListFilteredCard(
            navHostController,
            searchText,
            selectedCategory,
            coroutineScope,
            snackbarHostState,
            listId,
            booklistVM,
            booksId,
            padding
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddToListFilteredCard(
    navHostController: NavHostController,
    title: String,
    selectedCategory: BookCategory?,
    coroutineScope: CoroutineScope,
    hostState: SnackbarHostState,
    listId: String,
    booklistVM: BooklistVM,
    booksId: SnapshotStateList<Int>,
    paddingValues: PaddingValues
) {

    val filteredBooks by remember(title, selectedCategory) {
        derivedStateOf {
            books.filter { book ->
                book.id !in booksId &&
                        book.title.contains(title, ignoreCase = true) &&
                        (selectedCategory == null || book.category == selectedCategory)
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .background(Beige)
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        items(filteredBooks) { book ->
            AddToListCard(
                book.title,
                navHostController,
                coroutineScope,
                hostState,
                listId,
                book.id,
                booksId,
                booklistVM
            )
        }

    }

}


@Composable
fun AddToListCard(
    title: String,
    navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    hostState: SnackbarHostState,
    listId: String,
    bookId: Int,
    booksId: SnapshotStateList<Int>,
    booklistVM: BooklistVM
){
    val book = books[books.indexOfFirst { it.title == title }]
    val listVM: ListVM = viewModel()
    val listName = listVM.list.find { it.id == listId }?.name ?: "Unnamed"

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
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "More",
                        tint = DarkBlue,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd)
                            .clickable {
                                //TODO

                                booklistVM.addToBooklist(
                                    Booklist(
                                        listId = listId,
                                        bookId = bookId,
                                        userId = user.userId
                                    )
                                )

                                booksId.add(bookId)

                                coroutineScope.launch {
                                hostState.showSnackbar(
                                    message = "Added to $listName",
                                    duration = SnackbarDuration.Short
                                )
                            }  }
                    )
                }
            }
        }

    }

}