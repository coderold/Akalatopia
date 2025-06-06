package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

data class Review(
    val id: String? = null,
    val user: User = User(),
    val date: String = "",
    val bookId: Int = 0,
    val review: String = ""
){
    fun toJSON(): Map<String, Any?> =
        mapOf(
            "user" to mapOf(
                "userId" to user.userId,
                "name" to user.name,
                "userName" to user.userName,
                "bio" to user.bio,
                "avatar" to user.avatar
            ),
            "date" to date,
            "bookId" to bookId,
            "review" to review
        )
}

class FirebaseReviewVM: ViewModel(){
    private val database = Firebase.database("https://aklatopia-default-rtdb.asia-southeast1.firebasedatabase.app/")

    val reviews = mutableStateListOf<Review>()
    val ref = database.getReference("reviews")

    init {
        getReviewsRT()
    }

//    fun getReviews(){
//        ref
//            .get()
//            .addOnCompleteListener {task ->
//                task.result.getValue<Lists<Review>>()?.let {
//                    reviews.clear()
//                    reviews.addAll(it)
//                }
//            }
//    }

    private fun getReviewsRT() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot
            ) {
                reviews.clear()
                snapshot.children.forEach { child ->
                    child.getValue(Review::class.java)?.let { review ->
                        reviews.add(review.copy(id = child.key))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

    fun uploadReview(review: Review){
        ref
            .push()
            .setValue(review.toJSON())
    }

    fun deleteReview(reviewId: String){
        ref.child(reviewId).removeValue()
    }
}