package com.example.aklatopia.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.aklatopia.SupabaseClient
import com.example.aklatopia.home.components.Bookz
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.ktor.util.filter
import kotlinx.serialization.SerialName
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

//@Serializable
//data class User(
//    val id: Int? = null,
//    @SerialName("userId") val userId: String = "",
//    @SerialName("name") val name: String = "",
//    @SerialName("userName") var userName: String? = null,
//    @SerialName("bio") var bio: String? = null,
//    @SerialName("avatar") val avatar: String? = null
//)

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
                userName = "aa",
                bio = "ss",
                avatar = supabaseUser.userMetadata?.get("avatar_url")?.jsonPrimitive?.content ?: ""
            )
        } else {
            userState.value = User()
        }
    }

    suspend fun verifyUserIdInListAndInsertIfMissing(existingUserIds: List<String>) {
        val supabaseUser = SupabaseClient.client.auth.currentUserOrNull() ?: return

        Log.d("DEBUG", "Supabase user ID: ${supabaseUser.id}")
        Log.d("DEBUG", "Does current user exist in table: ${!existingUserIds.contains(supabaseUser.id)}")

        if (!existingUserIds.contains(supabaseUser.id)) {
            val newUser = User(
                userId = supabaseUser.id,
                name = supabaseUser.userMetadata?.get("name")?.jsonPrimitive?.content ?: "",
                userName = "aa",
                bio = "ss",
                avatar = supabaseUser.userMetadata?.get("avatar_url")?.jsonPrimitive?.content ?: ""
            )
            SupabaseClient.newUser(newUser)
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try {
            SupabaseClient.client
                .from("Users")
                .update (
                    {
                        set("name", user.name)
                        set("userName", user.userName)
                        set("bio", user.bio)
                    }
                ) {
                    filter {
                    eq("userId", user.userId)
                } }
            true
        } catch (e: Exception) {
            Log.e("Supabase", "Update failed: ${e.message}")
            false
        }
    }
}



