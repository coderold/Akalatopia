package com.example.aklatopia.list.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.data.ListVM
import com.example.aklatopia.data.List
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green

@Composable
fun ListHeader(
    label: String,
    navHostController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
){
    var showAddList by remember{ mutableStateOf(false) }
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                Box(modifier = Modifier
                    .background(Beige)
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(DarkBlue)
                    .fillMaxWidth()
                    .fillMaxHeight(if (isScreenRotated) 0.23f else 0.12f)
                )
                {
                    Text(
                        text = if (isScreenRotated) label else "Aklatopia",
                        style = MaterialTheme.typography.titleLarge,
                        color = Beige,
                        fontSize = 36.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                    if (isScreenRotated){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(40.dp)
                                .padding(end = 10.dp)
                                .clickable { showAddList = true },
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }

                }
                if (!isScreenRotated){
                    Box(
                        modifier = Modifier
                            .background(Beige)
                            .fillMaxWidth()
                    ){
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = DarkBlue,
                            fontSize = 32.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(DarkBlue)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }

            }
        },
        floatingActionButton = {
            if (!isScreenRotated){
                FloatingActionButton(
                    onClick = { showAddList = true },
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
        floatingActionButtonPosition = FabPosition.End
    ){
            padding ->
        content(padding)
    }
    if (showAddList){
        Dialog(onDismissRequest = {showAddList = false}){
            var listName by remember { mutableStateOf("") }
            AddListDialog(
                onDismiss = {showAddList = false},
                onListNameChange = { listName = it},
                listName = listName,
                onConfirm = {
                    if (listName.isNotEmpty()){
                        ListVM().createList(
                            List(
                                user = SupabaseUser.userState.value,
                                name = listName,
                            )
                        )
                        Toast.makeText(context, "List Created!", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(context, "Empty Field", Toast.LENGTH_SHORT).show()
                    }
                    showAddList = false
                }
            )
        }
    }
}