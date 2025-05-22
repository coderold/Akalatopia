package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.collections.List

data class Booklist(
    val id: String? = null,
    val listId: String = "",
    val bookId: Int = 0,
    val userId: String = ""
) {
    fun toJSON(): Map<String, Any?> =
        mapOf(
            "listId" to listId,
            "bookId" to bookId,
            "userId" to userId
        )
}

class BooklistVM : ViewModel() {
    private val database = Firebase.database("https://aklatopia-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val _booklist = mutableStateListOf<Booklist>()
    val booklist: List<Booklist> get() = _booklist

    private val ref = database.getReference("booklist")

    init {
        getBooklistRT()
    }

    private fun getBooklistRT() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _booklist.clear()
                snapshot.children.forEach { child ->
                    child.getValue(Booklist::class.java)?.let { item ->
                        _booklist.add(item.copy(id = child.key))
                    }
                }
                Log.d("BooklistVM", "Booklist updated: ${_booklist.size} items")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

    fun addToBooklist(bookList: Booklist) {
        ref.push().setValue(bookList.toJSON())
    }

//    fun removeFromBooklist(id: Int) {
//        ref.child(id).removeValue()
//    }

    fun removeFromBooklistByBookId(bookId: Int, listId: String) {
        ref.orderByChild("bookId").equalTo(bookId.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        val booklist = child.getValue(Booklist::class.java)
                        if (booklist != null && booklist.listId == listId) {
                            child.ref.removeValue()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Delete failed: ${error.message}")
                }
            })
    }
}
