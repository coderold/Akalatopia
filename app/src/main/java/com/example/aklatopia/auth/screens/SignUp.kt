package com.example.aklatopia.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aklatopia.assets.BeigeBackButton
import com.example.aklatopia.assets.BlueBackButton
import com.example.aklatopia.R
import com.example.aklatopia.auth.components.InfoFields
import com.example.aklatopia.auth.components.PasswordFields
import com.example.aklatopia.auth.components.RoutedButton
import com.example.aklatopia.auth.components.StyledTextField
import com.example.aklatopia.ui.theme.*

@Composable
fun SignUp(navHostController: NavHostController){
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val isScreenRotated = maxWidth > 450.dp
        Box(modifier = Modifier
            .background(Beige)
            .fillMaxSize()
        ){
            BlueBackButton(
                navHostController = navHostController,
                modifier = Modifier
                    .padding(top = 25.dp, start = 5.dp)
                    .alpha(if (isScreenRotated) 0f else 1f)
            )
            Text(
                text = if (isScreenRotated) "Aklatopia - Sign Up" else "Aklatopia",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                color = DarkBlue,
                fontSize = if (isScreenRotated) 26.sp else 48.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        top = if (isScreenRotated) 0.dp else 80.dp
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(if (isScreenRotated) 0.9f else 0.8f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp))
                    .background(DarkBlue)
            ){
                BeigeBackButton(
                    navHostController = navHostController,
                    modifier = Modifier
                        .padding(
                            top = 25.dp,
                            start = 30.dp
                        )
                        .alpha(if (isScreenRotated) 1f else 0f)
                )

                Text(
                    text = "Sign Up",
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    color = Beige,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .alpha(if (isScreenRotated) 0f else 1f)
                )

                var name by remember{
                    mutableStateOf("")
                }
                var user by remember{
                    mutableStateOf("")
                }
                var email by remember{
                    mutableStateOf("")
                }
                var pass by remember{
                    mutableStateOf("")
                }
                var confirm by remember{
                    mutableStateOf("")
                }

                Column (
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = if (isScreenRotated) 0.dp else 100.dp)
                        .fillMaxWidth(0.8f)
                ) {
                    StyledTextField(
                        label = "Name",
                        value = name,
                        onValueChange = {name = it},
                        paddingValues = PaddingValues(0.dp, 10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    if (!isScreenRotated){
                        InfoFields(
                            modifier = Modifier
                                .fillMaxWidth(),
                            userValue = user,
                            onUserValueChange = {user = it},
                            emailValue = email,
                            onEmailValueChange = {email = it}
                        )
                        PasswordFields(
                            modifier = Modifier
                                .fillMaxWidth(),
                            passValue = pass,
                            onPassValueChange = {pass = it},
                            confirmValue = confirm,
                            onConfirmValueChange = {confirm = it}
                        )
                    }
                    else{
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            InfoFields(
                                modifier = Modifier
                                .fillMaxWidth(0.5f),
                                userValue = user,
                                onUserValueChange = {user = it},
                                emailValue = email,
                                onEmailValueChange = {email = it}

                            )
                            PasswordFields(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                passValue = pass,
                                onPassValueChange = {pass = it},
                                confirmValue = confirm,
                                onConfirmValueChange = {confirm = it}
                            )
                        }
                    }

                    RoutedButton(
                        label = "Create Account",
                        navHostController = navHostController,
                        route = "main",
                        color = Red,
                        modifier = Modifier
                            .padding(0.dp, 20.dp)
                            .height(60.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
