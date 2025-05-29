package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

data class Rating(
    val id: String? = null,
    val rating: Int = 0,
    val bookId: Int = 0,
    val category: String = "",
    val userId: String = ""
){
    fun toJSON(): Map<String, Any?> =
        mapOf(
            "rating" to rating,
            "bookId" to bookId,
            "category" to category,
            "userId" to userId
        )
}

class FirebaseRatingsVM: ViewModel(){
    private val database = Firebase.database("https://aklatopia-default-rtdb.asia-southeast1.firebasedatabase.app/")

    val ratings = mutableStateListOf<Rating>()
    val ref = database.getReference("ratings")

    init {
        getRatingsRT()
    }

    private fun getRatingsRT() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot
            ) {
                ratings.clear()
                snapshot.children.forEach { child ->
                    child.getValue(Rating::class.java)?.let { rating ->
                        ratings.add(rating.copy(id = child.key))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

    fun uploadRating(rating: Rating){
        ref
            .push()
            .setValue(rating.toJSON())
    }
}


