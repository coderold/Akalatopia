package com.example.aklatopia.home.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.data.BookCategory
import kotlinx.serialization.Serializable
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Contextual

@Serializable
data class Bookz(
    val id: Int,
    val title: String,
    val cover: String,
    val desc: String,
    val year: String,
    val author: String,
    val category: String,
    val synopsis: String,
//    val ratings: Double,
//    val totalRatings: Int,
//    val totalReviews: Int,
)

class SupabaseRepository {
    suspend fun fetchItems(): List<Bookz> {
        return SupabaseClient.client
            .from("Books")
            .select()
            .decodeList<Bookz>()
    }
}

class BookVM: ViewModel(){
    private val repository = SupabaseRepository()
    private val _items = MutableStateFlow<List<Bookz>>(emptyList())
    val items: StateFlow<List<Bookz>> = _items

    fun fetchItems() {
        viewModelScope.launch {
            try {
                _items.value = repository.fetchItems()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}


