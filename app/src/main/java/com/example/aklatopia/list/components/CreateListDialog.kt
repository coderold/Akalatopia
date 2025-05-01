package com.example.aklatopia.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Red

@Composable
fun AddListDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var listName by remember { mutableStateOf("") }
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Beige
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Give your list a name!",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = listName,
                onValueChange = { listName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(OffWhite),
                placeholder = { Text("Example: My List") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = OffWhite,
                    focusedContainerColor = OffWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = onConfirm ,
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text(
                        text = "Create",
                        color = Beige,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}