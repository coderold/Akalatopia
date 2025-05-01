package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

data class Review(
    val id: String? = null,
    val profilePic: String = "",
    val name: String = "",
    val date: String = "",
    val bookId: String = "",
    val review: String = ""
){
    fun toJSON(): Map<String, Any?> =
        mapOf(
            "profilePic" to profilePic,
            "name" to name,
            "date" to date,
            "bookId" to bookId,
            "review" to review
        )
}

class FirebaseReviewVM: ViewModel(){
    private val database = Firebase.database("https://aklatopia-default-rtdb.asia-southeast1.firebasedatabase.app/")

    val reviews = mutableStateListOf<Review>()

    init {
        getReviewsRT()
    }

    fun getReviews(){
        database.getReference("reviews")
            .get()
            .addOnCompleteListener {task ->
                task.result.getValue<List<Review>>()?.let {
                    reviews.clear()
                    reviews.addAll(it)
                }
            }
    }

    fun getReviewsRT() {
        database.getReference("reviews").addValueEventListener(object : ValueEventListener {
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
        database.getReference("reviews")
            .push()
            .setValue(review.toJSON())
    }
}

//val Reviews = arrayListOf<Review>(
//    Review(
//        profilePic = R.drawable.user2_profile,
//        name = "Myra Geanga",
//        date = "March 1, 2025",
//        bookId = "0",
//        review = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
//                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
//                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
//    ),
//    Review(
//        profilePic = R.drawable.user1_profile,
//        name = "Anjelo Iyo",
//        date = "March 2, 2025",
//        bookId = "1",
//        review = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
//                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
//                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
//    ),
//    Review(
//        profilePic = R.drawable.user_profile_pic,
//        name = "Matthew Molina",
//        date = "March 3, 2025",
//        bookId = "2",
//        review = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
//                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
//                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
//    ),
//)