package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

data class Favorite (
    val id: String? = null,
    val bookId: Int = 0,
    val userId: String = ""
){
    fun toJSON(): Map<String, Any?> =
        mapOf(
            "bookId" to bookId,
            "userId" to userId
        )
}

class FavoritesVM: ViewModel(){
    private val database = Firebase.database("https://aklatopia-default-rtdb.asia-southeast1.firebasedatabase.app/")

    val favorites = mutableStateListOf<Favorite>()
    val ref = database.getReference("reviews")

    init {
        getFavoritesRT()
    }

    private fun getFavoritesRT() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot
            ) {
                favorites.clear()
                snapshot.children.forEach { child ->
                    child.getValue(Favorite::class.java)?.let { item ->
                        favorites.add(item.copy(id = child.key))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

    fun addToFavorites(favorite: Favorite){
        ref
            .push()
            .setValue(favorite.toJSON())
    }

    fun removeFromFavorites(id: String){
        ref.child(id).removeValue()
    }
}
