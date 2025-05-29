package com.example.aklatopia.profile.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.WindowInfo
import com.example.aklatopia.assets.ConfirmDialog
import com.example.aklatopia.assets.Line
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.data.User
import com.example.aklatopia.rememberWindowInfo
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Red
import com.example.aklatopia.uploadImageToImgbb
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditProfileDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    userState: MutableState<User>,
    profileUri: MutableState<Uri?>
) {
    val windowInfo = rememberWindowInfo()
    val isScreenRotated = windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium

    var name by remember { mutableStateOf(userState.value.name) }
    var username by remember { mutableStateOf(userState.value.userName) }
    var bio by remember { mutableStateOf(userState.value.bio) }

    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    var showConfirmEditDialog by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Beige,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            stickyHeader {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface )
                ) {
                    Text(
                        text = "Edit Profile",
                        fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                        fontSize = if (isScreenRotated) 18.sp else 24.sp,
                        color = DarkBlue,
                    )
                    Line()
                }
            }

            item{
                CustomSpacer(isScreenRotated)
            }


            item{
                Card (
                    shape = RoundedCornerShape(75.dp),
                    modifier = Modifier
                        .size(if (isScreenRotated) 80.dp else 120.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DarkBlue.copy(alpha = 0.4f))
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Beige,
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    }


                }
            }

            item{
                CustomSpacer(isScreenRotated)
            }

            //name textfield
            item {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OffWhite),
                    label = {
                        Text(
                            text = "Name",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 16.sp,
                            color = DarkBlue,
                        )
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            item{
                CustomSpacer(isScreenRotated)
            }

            //username Textfield
            item {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OffWhite),
                    label = {
                        Text(
                            text = "Username",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 16.sp,
                            color = DarkBlue,
                        )
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            item{
                CustomSpacer(isScreenRotated)
            }

            item{
                TextField(
                    value = bio,
                    onValueChange = { bio = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OffWhite),
                    label = {
                        Text(
                            text = "Bio",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 16.sp,
                            color = DarkBlue,
                        )
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            item{
                CustomSpacer(isScreenRotated)
            }

            item{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.weight(1f).height(40.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            color = Beige,
                            fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            userState.value = userState.value.copy(
                                name = name,
                                userName = username,
                                bio = bio
                            )

                            coroutineScope.launch {
                                imageUri.value?.let {
                                    uploadImageToImgbb(context, it,
                                        onSuccess = { url ->
                                            Log.d("UPLOAD", "Image URL: $url")
                                            userState.value.avatar = url
                                        }
                                    ) { err -> Log.e("UPLOAD", err) }
                                }
                            }

                            showConfirmEditDialog = true
                        }
                        ,
                        colors = ButtonDefaults.buttonColors(containerColor = Green),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.weight(1f).height(40.dp)
                    ) {
                        Text(
                            text = "Confirm",
                            color = Beige,
                            fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                        )
                    }
                }
            }
        }
    }

    if (showConfirmEditDialog){
        ConfirmDialog(
            label = "Save Changes?",
            onDismiss = {showConfirmEditDialog = false},
            onConfirm = {
                coroutineScope.launch {
                    userState.value = userState.value.copy(
                        name = name,
                        userName = username,
                        bio = bio
                    )

                    imageUri.value?.let { uri ->
                        // Upload image
                        uploadImageToImgbb(context, uri,
                            onSuccess = { url ->
                                userState.value = userState.value.copy(avatar = url)

                                coroutineScope.launch {
                                    SupabaseUser.updateUser(userState.value)
                                    profileUri.value = uri
                                    onConfirm()
                                }
                            },
                            onError = { err ->
                                Log.e("UPLOAD", err)
                            }
                        )
                    } ?: run {
                        SupabaseUser.updateUser(userState.value)
                        onConfirm()
                    }

                    showConfirmEditDialog = false
                }
            }
        )
    }
}



@Composable
fun CustomSpacer(isScreenRotated: Boolean){
    Spacer(modifier = Modifier.height(if (isScreenRotated) 10.dp else 20.dp))
}