package com.example.aklatopia.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.home.components.Bookz
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.jsonPrimitive
import kotlin.collections.List

@Serializable
data class User(
    val id: Int? = null,
    val userId: String = "",
    val name: String = "",
    var userName: String = "",
    var bio: String = "",
    val avatar: String = "",
)


object SupabaseUser {
    val userState = mutableStateOf(User())

    private suspend fun fetchUsers(): List<User> {
        return SupabaseClient.client
            .from("Users")
            .select()
            .decodeList<User>()
    }

    suspend fun refreshUser() {
        val supabaseUser = SupabaseClient.client.auth.currentUserOrNull()

        if (supabaseUser != null) {
            userState.value = User(
                userId = supabaseUser.id,
                name = supabaseUser.userMetadata?.get("name")?.jsonPrimitive?.content ?: "",
                userName = "",
                bio = "",
                avatar = supabaseUser.userMetadata?.get("avatar_url")?.jsonPrimitive?.content ?: ""
            )

            //SupabaseClient.newUser(userState.value)
        } else {
            userState.value = User()
        }
    }
}



