package com.example.aklatopia.home.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.R
import com.example.aklatopia.assets.ConfirmDialog
import com.example.aklatopia.data.FirebaseReviewVM
import com.example.aklatopia.data.Review
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Yellow
import kotlinx.coroutines.launch

@Composable
fun ReviewCard(review: Review, viewModel: FirebaseReviewVM){
    var expanded by remember { mutableStateOf(false) }

    var showDeleteReviewDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .background(Yellow)
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ){
                OnlineImage(
                    imageUrl = review.user.avatar,
                    contentDescription = "ProfilePic",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(25.dp))
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = review.user.name,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 16.sp,
                        color = DarkBlue
                    )

                    Text(
                        text = review.date,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp,
                        color = DarkBlue
                    )
                }

                if (review.user.userId == SupabaseUser.userState.value.userId){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More",
                                tint = DarkBlue,
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(Beige)
                                .align(Alignment.TopCenter)
                        ) {
                            DropdownMenuItem(
                                text = { Text(
                                    text = "Delete Review",
                                    color = DarkBlue,
                                    fontFamily = FontFamily(Font(R.font.poppins_extrabold))
                                ) },
                                onClick = {
                                    showDeleteReviewDialog = true
                                }
                            )
                        }
                    }
                }

            }

            Text(
                text = review.review,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = DarkBlue,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }

    }

    if (showDeleteReviewDialog){
        ConfirmDialog(
            label = "Delete Review?",
            onDismiss = {showDeleteReviewDialog = false},
            onConfirm = {
                expanded = false
                viewModel.deleteReview(review.id.toString())
                showDeleteReviewDialog = false

                Toast.makeText(context, "Review Deleted", Toast.LENGTH_SHORT).show()
            }
        )
    }
}