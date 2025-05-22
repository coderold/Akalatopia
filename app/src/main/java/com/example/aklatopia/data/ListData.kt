package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

data class List(
    val id: String? = null,
    val user: User = User(),
    val name: String = "",
){
    fun toJSON(): Map<String, Any?> =
        mapOf(
            "name" to name,
            "user" to mapOf(
                "userId" to user.userId,
                "name" to user.name,
                "userName" to user.userName,
                "bio" to user.bio,
                "avatar" to user.avatar
            ),
        )
}

class ListVM: ViewModel(){
    private val database = Firebase.database("https://aklatopia-default-rtdb.asia-southeast1.firebasedatabase.app/")

    val list = mutableStateListOf<List>()
    val ref = database.getReference("list")

    init {
        getListRT()
    }

    private fun getListRT() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot
            ) {
                list.clear()
                snapshot.children.forEach { child ->
                    child.getValue(List::class.java)?.let { item ->
                        list.add(item.copy(id = child.key))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }

    fun createList(list: List){
        ref
            .push()
            .setValue(list.toJSON())
    }

    fun updateList(id: String?, newName: String){
        val updatedList = List(
            id = id,
            user = user,
            name = newName
        )
        ref.child(id.toString())
            .updateChildren(updatedList.toJSON())
    }

    fun deleteList(id: String){
        ref.child(id).removeValue()
    }

}