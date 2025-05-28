package com.example.aklatopia.profile.components

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.data.User
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.Red
import kotlinx.coroutines.launch

@Composable
fun SignOutAlert(
    onDismiss: () -> Unit,
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
                            SupabaseUser.userState.value = User()
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