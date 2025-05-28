package com.example.aklatopia.profile.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.data.BookCategory
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.assets.LabeledHeader
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.data.SupabaseUser.userState
import com.example.aklatopia.data.User
import com.example.aklatopia.home.components.Bookz
import com.example.aklatopia.profile.components.EditProfileDialog
import com.example.aklatopia.profile.components.ProfileInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.Red
import com.example.aklatopia.ui.theme.Yellow
import com.example.aklatopia.profile.components.SignOutAlert
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(navHostController: NavHostController){
    val scrollState = rememberScrollState()

    var progress = 0
    BookCategory.allCategories.forEach{ bookCategory->
        progress += bookCategory.progress
    }

    val imageUri = remember { mutableStateOf<Uri?>(null) }

    userState.value.userName =  if (userState.value.userName == "") "CreateUsername" else userState.value.userName
    userState.value.bio =  if (userState.value.bio == "") "Add a Bio" else userState.value.bio

    val users = remember { mutableStateListOf<User>() }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val result = SupabaseClient.client.from("Users").select().decodeList<User>()
            users.addAll(result)

            val userIds = result.map { it.userId }
            Log.d("DEBUG", "All user IDs: $userIds")

            SupabaseUser.verifyUserIdInListAndInsertIfMissing(userIds)
        }
    }

    LabeledHeader(label = "Profile") {padding ->
    Column (
        modifier = Modifier
            .padding(padding)
            .verticalScroll(
                state = scrollState
            )
        ){
            ProfileInfo(imageUri)
            Line()
            OverallProgressBar((progress/6), navHostController)
            Line()
            ProfileButtonGroup(userState = userState, imageUri = imageUri)
        }
    }
}

@Composable
fun OverallProgressBar(progress: Int, navHostController: NavHostController){
    Box(
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
            .height(150.dp)
            .clickable { navHostController.navigate("progress") }
    ){
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(5.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Yellow)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    ExtraBoldText(
                        text = "Progress",
                        size = 24.sp,
                        color = DarkBlue,
                        modifier = Modifier
                    )
                    ExtraBoldText(
                        text = "$progress%",
                        size = 24.sp,
                        color = DarkBlue,
                        modifier = Modifier
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                )
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(progress.toFloat() / 100)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Green)
                        .align(Alignment.BottomStart)
                )

            }

        }
    }
}

@Composable
fun ProfileButtonGroup(
    userState: MutableState<User>,
    imageUri: MutableState<Uri?>
){
    var showDialog by remember{
        mutableStateOf(false)
    }
    var showEditProfileDialog by remember{
        mutableStateOf(false)
    }

    if(showDialog){
        SignOutAlert(
            onDismiss = {showDialog = false}
        )
    }

    if(showEditProfileDialog){
        Dialog(onDismissRequest = {showEditProfileDialog = false}){
            EditProfileDialog(
                onDismiss = { showEditProfileDialog = false },
                onConfirm = {showEditProfileDialog = false},
                userState = userState,
                imageUri
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Button(
            onClick = { showEditProfileDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = Beige
            ),
            modifier = Modifier
                .width(180.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Edit Profile",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                fontSize = 20.sp,
            )
        }

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Red,
                contentColor = Beige
            ),
            modifier = Modifier
                .width(180.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Sign Out",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                fontSize = 20.sp,
            )
        }
    }
}