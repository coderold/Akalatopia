package com.example.aklatopia.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Yellow

@Composable
fun StyledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.poppins_semibold))
            )
        },
        textStyle = TextStyle(
            color = DarkBlue,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = OffWhite,
            unfocusedTextColor = DarkBlue,
            focusedContainerColor = OffWhite,
            focusedLabelColor = Green,
            focusedIndicatorColor = Yellow,
            cursorColor = DarkBlue
        ),
        modifier = modifier.then(
            Modifier
                .padding(paddingValues)
                .height(60.dp)
        )
    )
}

@Composable
fun PasswordTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.poppins_semibold))
            )
        },
        textStyle = TextStyle(
            color = DarkBlue,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        ),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = OffWhite,
            unfocusedTextColor = DarkBlue,
            focusedContainerColor = OffWhite,
            focusedLabelColor = Green,
            focusedIndicatorColor = Yellow,
            cursorColor = DarkBlue
        ),
        modifier = modifier.then(
            Modifier
                .padding(paddingValues)
                .height(60.dp)
        )
    )
}

@Composable
fun InfoFields(
    modifier: Modifier,
    userValue: String,
    onUserValueChange: (String) -> Unit,
    emailValue: String,
    onEmailValueChange: (String) -> Unit,
){
    Column(modifier = modifier) {
        StyledTextField(
            label = "Username",
            value = userValue,
            onValueChange = onUserValueChange,
            paddingValues = PaddingValues(0.dp, 10.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        StyledTextField(
            label = "Email",
            value = emailValue,
            onValueChange = onEmailValueChange,
            paddingValues = PaddingValues(0.dp, 10.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun PasswordFields(
    modifier: Modifier,
    passValue: String,
    onPassValueChange: (String) -> Unit,
    confirmValue: String,
    onConfirmValueChange: (String) -> Unit,
) {
    Column(modifier = modifier) {
        PasswordTextField(
            label = "Password",
            value = passValue,
            onValueChange = onPassValueChange,
            paddingValues = PaddingValues(0.dp, 10.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        PasswordTextField(
            label = "Confirm Password",
            value = confirmValue,
            onValueChange = onConfirmValueChange,
            paddingValues = PaddingValues(0.dp, 10.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}