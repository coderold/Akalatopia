package com.example.aklatopia.profile.screens

import android.app.Activity
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.aklatopia.data.User
import com.example.aklatopia.data.user
import com.example.aklatopia.profile.components.EditProfileDialog
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.Red
import com.example.aklatopia.ui.theme.Yellow
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navHostController: NavHostController){
    val scrollState = rememberScrollState()

    var progress = 0
    BookCategory.allCategories.forEach{ bookCategory->
        progress += bookCategory.progress
    }

    val userState = remember { mutableStateOf(user) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    LabeledHeader(label = "Profile") {padding ->
    Column (
        modifier = Modifier
            .padding(padding)
            .verticalScroll(
                state = scrollState
            )
        ){
            ProfileInfo(userState, imageUri)
            Line()
            OverallProgressBar((progress/6), navHostController)
            Line()
            ProfileButtonGroup(navHostController, userState = userState, imageUri = imageUri)
        }
    }
}

@Composable
fun ProfileInfo(userState: MutableState<User>, imageUri: MutableState<Uri?>){
    Box(
        modifier = Modifier
            .background(Beige)
            .fillMaxWidth()
            .height(300.dp)
    ){
        Card (
            shape = RoundedCornerShape(75.dp),
            modifier = Modifier
                .padding(20.dp)
                .size(100.dp)
                .align(Alignment.TopCenter)
        ) {

            if (imageUri.value != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri.value),
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                OnlineImage(
                    imageUrl = userState.value.avatar,
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

        }
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 125.dp, start = 20.dp)
        ) {
            Text(
                text = userState.value.name,
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                fontSize = 24.sp,
                color = DarkBlue
            )
            Text(
                text = userState.value.userName,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontSize = 15.sp,
                color = DarkBlue
            )
        }

        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Yellow)
                .align(Alignment.BottomCenter)

        ){
            Text(
                text = userState.value.bio,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = DarkBlue,
                modifier = Modifier
                    .align(Alignment.Center)
            )
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
    navHostController: NavHostController,
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

@Composable
fun SignOutAlert(
    onDismiss: () -> Unit
){
    val context = LocalContext.current
    val activity = context as? Activity
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        modifier = Modifier
            .height(200.dp),
        containerColor = Beige,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Are you Sure?",
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    fontSize = 32.sp,
                    color = DarkBlue,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        },

        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        contentColor = Beige
                    ),
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                        fontSize = 15.sp,
                    )
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            SupabaseClient.logout()
                            Toast.makeText(context,"Signed Out", Toast.LENGTH_SHORT).show()
                        }

                        activity?.let {
                        it.finish()
                        it.startActivity(it.intent) }
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red,
                        contentColor = Beige
                    ),
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Sign Out",
                        fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                        fontSize = 15.sp,
                    )
                }
            }
        }
    )
}

