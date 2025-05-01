package com.example.aklatopia.list.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.data.bookList
import com.example.aklatopia.list.components.ListBookCard
import com.example.aklatopia.list.components.ListContentHeader
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green

@Composable
fun ListContentScreen(
    navHostController: NavHostController,
    listName: String
){
    ListContentHeader(
        navHostController,
        label = listName
    ){ paddingValues ->
        ListContent(listName,navHostController,paddingValues)
    }

}

@Composable
fun ListContent(
    listName: String,
    navHostController: NavHostController,
    paddingValues: PaddingValues
){
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium
    Scaffold (
        floatingActionButton = {
            if (!isScreenRotated){
                FloatingActionButton(
                    onClick = { navHostController.navigate("addToList/$listName")},
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
            val bookList = bookList[bookList.indexOfFirst { it.listName == listName}]
            items(bookList.books){ book->
                ListBookCard(
                    book.title,
                    label = "Remove From List",
                    navHostController,
                    onClick = {},
                )
            }
        }
    }
}