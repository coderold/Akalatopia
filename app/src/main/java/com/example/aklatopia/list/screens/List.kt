package com.example.aklatopia.list.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
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
import androidx.navigation.NavHostController
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.data.bookList
import com.example.aklatopia.list.components.ListHeader
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Yellow

@Composable
fun ListScreen(navHostController: NavHostController){
    ListHeader(
        label = "List",
        navHostController){
        paddingValues ->
        Lists(navHostController,paddingValues)
    }

}

@Composable
fun Lists(navHostController: NavHostController,paddingValues: PaddingValues){
    LazyColumn(
        modifier = Modifier
            .background(Beige)
            .fillMaxSize()
            .padding(paddingValues)
    ){
        items(bookList){ bookList->
            ListCard(
                bookList.listName,
                navHostController,
                onEdit = {},
                onDelete = {}
            )
        }

    }
}

@Composable
fun ListCard(listName: String, navHostController: NavHostController){
    val bookList = bookList[bookList.indexOfFirst { it.listName == listName}]
    Card (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable { navHostController.navigate("listContent/$listName") },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Yellow)
        ){
            Row{
                Text(
                    text = bookList.listName,
                    color = DarkBlue,
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(24.dp)
                        .requiredWidth(260.dp)
                )
            }
        }

    }
}

@Composable
fun ListCard(
    listName: String,
    navHostController: NavHostController,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val bookList = bookList[bookList.indexOfFirst { it.listName == listName }]
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable { navHostController.navigate("listContent/$listName") },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Yellow)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bookList.listName,
                    color = DarkBlue,
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    fontSize = 20.sp
                )

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = DarkBlue,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Beige)
                    ) {
                        DropdownMenuItem(
                            text = { Text(
                                text = "Edit",
                                color = DarkBlue,
                                fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                            ) },
                            onClick = {
                                expanded = false
                                onEdit()
                            }
                        )
                        Line()
                        DropdownMenuItem(
                            text = { Text(
                                text = "Delete",
                                color = DarkBlue,
                                fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                            ) },
                            onClick = {
                                expanded = false
                                onDelete()
                            }
                        )
                    }
                }
            }
        }
    }
}