package com.example.aklatopia.list.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.assets.ConfirmDialog
import com.example.aklatopia.data.ListVM
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.list.components.AddListDialog
import com.example.aklatopia.list.components.ConfirmListDeleteDialog
import com.example.aklatopia.list.components.EditListDialog
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
    val ListVM = ListVM()

    LazyColumn(
        modifier = Modifier
            .background(Beige)
            .fillMaxSize()
            .padding(paddingValues)
    ){

        val myLists = ListVM.list.filter { it.user.userId == SupabaseUser.userState.value.userId }

        if (myLists.isEmpty()){
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ){
                    Text(
                        text = "Create your first List!",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 30.dp)
                    )
                }

            }
        }
        items(myLists){ item->
            ListCard(
                item.id.toString(),
                item.name,
                navHostController
            )

        }

    }
}

@Composable
fun ListCard(
    id: String,
    listName: String,
    navHostController: NavHostController,
) {

    var expanded by remember { mutableStateOf(false) }
    var showEditList by remember{ mutableStateOf(false) }
    var showDeleteList by remember{ mutableStateOf(false) }
    var showConfirmEditList by remember{ mutableStateOf(false) }
    var newListName by remember { mutableStateOf(listName) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable { navHostController.navigate("listContent/$id") },
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
                    text = listName,
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
                                showEditList = true
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
                                showDeleteList = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showEditList){
        Dialog(onDismissRequest = {showEditList = false}){
            EditListDialog(
                onDismiss = {showEditList = false},
                onListNameChange = { newListName = it},
                listName = newListName,
                onConfirm = {
                    if (listName.isNotEmpty()){

                        showConfirmEditList = true

                    }else{
                        Toast.makeText(context,"Empty Field", Toast.LENGTH_SHORT).show()
                    }

                }
            )
        }
    }

    if (showConfirmEditList){
        ConfirmDialog(
            label = "Save Changes?",
            onDismiss = {showConfirmEditList = false},
            onConfirm = {
                ListVM().updateList(
                    id = id,
                    newName = newListName,
                )
                showEditList = false
                Toast.makeText(context,"List name changed to $newListName", Toast.LENGTH_SHORT).show()

                showConfirmEditList = false
            }
        )
    }

    if (showDeleteList){
        Dialog(onDismissRequest = {showDeleteList = false}){
            ConfirmListDeleteDialog(
                onDismiss = {showDeleteList = false},
                onConfirm = {
                    ListVM().deleteList(id = id)
                    showDeleteList = false
                },
                listName = listName
            )
        }
    }
}