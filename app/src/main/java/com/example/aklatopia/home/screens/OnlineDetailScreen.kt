package com.example.aklatopia.home.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.aklatopia.OnlineImage
import com.example.aklatopia.assets.ExtraBoldText
import com.example.aklatopia.assets.Line
import com.example.aklatopia.R
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.data.FirebaseRatingsVM
import com.example.aklatopia.data.FirebaseReviewVM
import com.example.aklatopia.data.Rating
import com.example.aklatopia.data.Review
import com.example.aklatopia.data.books
import com.example.aklatopia.data.user
import com.example.aklatopia.home.components.AskForReview
import com.example.aklatopia.home.components.BookVM
import com.example.aklatopia.home.components.Bookz
import com.example.aklatopia.home.components.DetailHeader
import com.example.aklatopia.home.components.RateDialog
import com.example.aklatopia.home.components.RatingsDisplay
import com.example.aklatopia.home.components.ReviewCard
import com.example.aklatopia.home.components.ReviewDialog
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Green
import com.example.aklatopia.ui.theme.OffWhite
import com.example.aklatopia.ui.theme.Red
import com.example.aklatopia.ui.theme.Yellow
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnlineDetailScreen(
    navHostController: NavHostController,
    id: Int,
    ReviewVM: FirebaseReviewVM,
    RatingVM: FirebaseRatingsVM
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val SupabaseBooks = remember { mutableStateListOf<Bookz>() }

    val currentDate = LocalDate.now()
    val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))

    LaunchedEffect(Unit){
        withContext(Dispatchers.IO){
            val result = SupabaseClient.client.from("Books").select().decodeList<Bookz>()
            SupabaseBooks.addAll(result)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    containerColor = DarkBlue,
                    contentColor = Beige
                ) {
                    Text(data.visuals.message)
                }
            }
        },
        content = { padding ->
            DetailHeader(navHostController) { headerPadding ->
                var showReviewDialog by remember { mutableStateOf(false) }
                var showRateDialog by remember { mutableStateOf(false) }
                var showAskDialog by remember { mutableStateOf(false) }
                LazyColumn(
                    modifier = Modifier
                        .background(Beige)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(padding)
                        .padding(headerPadding),
                    horizontalAlignment = Alignment.Start,
                ) {

                    //filtered
                    val book = SupabaseBooks.find { it.id == id }

                    if (book == null) {
                        item {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = DarkBlue)
                            }
                        }
                        return@LazyColumn
                    }

                    item {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Card(
                                modifier = Modifier
                                    .requiredSize(350.dp)
                                    .padding(10.dp)
                                    .align(Alignment.Center),
                                shape = RoundedCornerShape(20.dp),
                                elevation = CardDefaults.cardElevation(5.dp)
                            ) {
                                OnlineImage(
                                    imageUrl = book.cover,
                                    contentDescription = book.desc,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .background(OffWhite)
                                        .fillMaxSize()

                                )
                            }
                        }
                        Text(
                            text = book.title,
                            fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                            color = DarkBlue,
                            fontSize = 36.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                        Text(
                            text = "Author: ${book.author} (${book.year})",
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            color = DarkBlue,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                        Text(
                            text = book.synopsis,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = DarkBlue,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Added to Favorites",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(190.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Red
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = Yellow,
                                    modifier = Modifier
                                        .size(22.dp)
                                )
                                Text(
                                    text = "Add to Favorites",
                                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                                    color = Beige,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                )
                            }
                            Button(
                                onClick = {showRateDialog = true},
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(190.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkBlue
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Yellow,
                                    modifier = Modifier
                                        .size(22.dp)
                                )
                                Text(
                                    text = "Rate this Book",
                                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                                    color = Beige,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                )
                            }
                        }
                        if (showRateDialog) {
                            Dialog(onDismissRequest = { showRateDialog = false }) {
                                RateDialog(
                                    onDismiss = { showRateDialog = false },
                                    onRate = { selectedStars ->
                                        RatingVM.uploadRating(
                                            Rating(
                                                rating = selectedStars,
                                                bookId = book.id,
                                                userId = "asdasdasd"
                                            )
                                        )
                                        showRateDialog = false
                                        showAskDialog = true
                                    }
                                )
                            }
                        }

                        if (showAskDialog) {
                            Dialog(onDismissRequest = { showAskDialog = false }) {
                                AskForReview(
                                    onDismiss = { showAskDialog = false },
                                    onConfirm = {
                                        showAskDialog = false
                                        showReviewDialog = true
                                    }
                                )
                            }
                        }

                        if (showReviewDialog) {
                            Dialog(onDismissRequest = { showReviewDialog = false }) {
                                ReviewDialog(
                                    onDismiss = { showReviewDialog = false },
                                    onPost = { reviewText ->
                                         ReviewVM.uploadReview(
                                             Review(
                                                 user = user,
                                                 date = formattedDate,
                                                 bookId = book.id,
                                                 review = reviewText
                                             )
                                        )
                                        showReviewDialog = false
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Review Posted",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    },
                                    profilePic = R.drawable.user_profile_pic,
                                    handle = "@pusangpagod",
                                    username = "Matthew Molina"
                                )
                            }
                        }
                    }

                    val filteredReviews = ReviewVM.reviews.filter { it.bookId == book.id }
                    val filteredRatings = RatingVM.ratings.filter { it.bookId == book.id }

                    val averageRating = if (filteredRatings.isNotEmpty()) {
                        filteredRatings.map { it.rating }.average()
                    } else {
                        0.0
                    }

                    item {
                        Line()
                        RatingsDisplay(
                            ratings = averageRating,
                            totalRatings = filteredRatings.size,
                            totalReviews = filteredReviews.size
                        )
                        Line()
                    }

                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .background(Beige)
                                .fillMaxWidth()
                        ) {
                            ExtraBoldText(
                                text = "Reviews",
                                size = 24.sp,
                                color = DarkBlue,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(10.dp)
                            )
                        }
                    }

                    if (filteredReviews.isNotEmpty()) {
                        items(filteredReviews) { review ->
                            ReviewCard(review, ReviewVM)
                        }
                    } else {
                        item {
                            Box(
                                modifier = Modifier
                                    .background(Beige)
                                    .fillMaxWidth()
                            ) {
                                ExtraBoldText(
                                    text = "This book has no reviews yet.",
                                    size = 16.sp,
                                    color = DarkBlue,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(10.dp)
                                )
                            }
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .background(Beige)
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Button(
                                onClick = {
                                    showReviewDialog = true
                                },
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(190.dp)
                                    .align(Alignment.Center),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Green
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Yellow,
                                    modifier = Modifier
                                        .size(22.dp)
                                )
                                Text(
                                    text = "Leave A Review",
                                    fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                                    color = Beige,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                )
                            }
                        }

                        if (showReviewDialog) {
                            Dialog(onDismissRequest = { showReviewDialog = false }) {
                                ReviewDialog(
                                    onDismiss = { showReviewDialog = false },
                                    onPost = { reviewText ->

                                        if (reviewText.isNotEmpty()){
                                            ReviewVM.uploadReview(
                                                Review(
                                                    user = user,
                                                    date = formattedDate,
                                                    bookId = book.id,
                                                    review = reviewText
                                                )
                                            )

                                            showReviewDialog = false
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "Review Posted",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        } else {
                                            showReviewDialog = false
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "WALANG LAMANNN, SAYANG STORAGE SA DATABASE!!! " +
                                                            "DI MO NAMAN BABAYARAN YAN!!",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }

                                    },

                                    //user info
                                    profilePic = R.drawable.user_profile_pic,
                                    handle = "@pusangpagod",
                                    username = "Matthew Molina"
                                )
                            }
                        }
                    }


                }

            }
        }
    )
}