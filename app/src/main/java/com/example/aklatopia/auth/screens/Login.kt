package com.example.aklatopia.auth.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aklatopia.GoogleSignInButton
import com.example.aklatopia.assets.BeigeBackButton
import com.example.aklatopia.assets.BlueBackButton
import com.example.aklatopia.R
import com.example.aklatopia.auth.components.PasswordTextField
import com.example.aklatopia.auth.components.RoutedButton
import com.example.aklatopia.auth.components.StyledTextField
import com.example.aklatopia.ui.theme.*

@Composable
fun Login(navHostController: NavHostController){
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ){
        val isScreenRotated = maxWidth > 450.dp
        Box(
            modifier = Modifier
                .background(Beige)
                .fillMaxSize()
        ){

            BlueBackButton(
                navHostController = navHostController,
                modifier = Modifier
                    .padding(
                        top = if (isScreenRotated) 10.dp else 25.dp,
                        start = 5.dp
                    )
                    .alpha(if (isScreenRotated) 0f else 1f)
            )
            Text(
                text = if (isScreenRotated) "Aklatopia - Log In" else "Aklatopia",
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
                    text = "Log In",
                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                    color = Beige,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .alpha(if (isScreenRotated) 0f else 1f)
                )
                var email by remember{
                    mutableStateOf("")
                }
                var pass by remember{
                    mutableStateOf("")
                }

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = if (isScreenRotated) 10.dp else 100.dp)
                ) {
                    StyledTextField(
                        label = "Email",
                        value = email,
                        onValueChange = {email = it},
                        paddingValues = PaddingValues(horizontal = 0.dp,
                            vertical = if (isScreenRotated) 5.dp else 20.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth(if (isScreenRotated) 0.5f else 0.8f)
                    )

                    PasswordTextField(
                        label = "Password",
                        value = pass,
                        onValueChange = {pass = it},
                        paddingValues = PaddingValues(horizontal = 0.dp,
                            vertical = if (isScreenRotated) 5.dp else 20.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth(if (isScreenRotated) 0.5f else 0.8f)
                    )

                    RoutedButton(
                        label = "Log In",
                        navHostController = navHostController,
                        route = "main",
                        color = Red,
                        modifier = Modifier
                            .padding(
                                horizontal = 0.dp,
                                vertical = if (isScreenRotated) 5.dp else 20.dp)
                            .fillMaxWidth(if (isScreenRotated) 0.5f else 0.8f)
                            .height(60.dp)
                    )

//                    Text(
//                        text = "Or Sign in With Google",
//                        color = Green,
//                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
//                        fontSize = 14.sp,
//                        modifier = Modifier
//                            .clickable {
//                                navHostController.navigate("main")
//                            }
//                            .align(Alignment.CenterHorizontally)
//                    )
                    GoogleSignInButton(navHostController)
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google",
                        modifier = Modifier
                            .size(53.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        buildAnnotatedString {
                            append("Don't have an Account? ")
                            withStyle(
                                style = SpanStyle(
                                    color = Red,
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                                )
                            ) {
                                append("Sign Up")
                            }
                        },
                        color = Green,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { navHostController.navigate("signup") }
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
