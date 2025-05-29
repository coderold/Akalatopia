package com.example.aklatopia.home.components

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aklatopia.R
import com.example.aklatopia.assets.ConfirmDialog
import com.example.aklatopia.data.Favorite
import com.example.aklatopia.data.FavoritesVM
import com.example.aklatopia.data.SupabaseUser
import com.example.aklatopia.ui.theme.Beige
import com.example.aklatopia.ui.theme.DarkBlue
import com.example.aklatopia.ui.theme.Red
import com.example.aklatopia.ui.theme.Yellow
import com.example.aklatopia.uploadImageToImgbb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddToFavoritesBtn(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    favoritesVM: FavoritesVM,
    bookId: Int,
    favoritesId: SnapshotStateList<Int>,
    isFavoriteBook: Boolean
) {

    var showRemoveFromFavDialog by remember { mutableStateOf(false) }

    if (isFavoriteBook){
        Button(
            onClick = {
                showRemoveFromFavDialog = true
            },
            modifier = Modifier
                .height(40.dp)
                .width(190.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow
            )
        ) {
            Text(
                text = "Remove From Favorites",
                fontFamily = FontFamily(Font(R.font.poppins_extrabold)),
                color = DarkBlue,
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
    } else {
        Button(
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Added to Favorites",
                        duration = SnackbarDuration.Short
                    )
                }
                favoritesVM.addToFavorites(
                    Favorite(
                        bookId = bookId,
                        userId = SupabaseUser.userState.value.userId
                    )
                )

                favoritesId.add(bookId)
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
    }

    if (showRemoveFromFavDialog){
        ConfirmDialog(
            label = "Remove from Favorites?",
            onDismiss = {showRemoveFromFavDialog = false},
            onConfirm = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Removed from Favorites",
                        duration = SnackbarDuration.Short
                    )
                }
                favoritesVM.removeFromFavoritesByBookId(bookId)
                favoritesId.remove(bookId)

                showRemoveFromFavDialog = false
            }
        )
    }

}
