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
    var name: String = "",
    var userName: String = "",
    var bio: String = "",
    var avatar: String = "",
)

object SupabaseUser {
    val userState = mutableStateOf(User())

//    private suspend fun fetchUsers(): List<User> {
//        return SupabaseClient.client
//            .from("Users")
//            .select()
//            .decodeList<User>()
//    }

    suspend fun refreshUser() {
        val supabaseUser = SupabaseClient.client.auth.currentUserOrNull()

        if (supabaseUser != null) {
            userState.value = User(
                userId = supabaseUser.id
            )
        } else {
            userState.value = User()
        }
    }

    suspend fun verifyUserIdInListAndInsertIfMissing(existingUserIds: List<String>) {
        val supabaseUser = SupabaseClient.client.auth.currentUserOrNull() ?: return
        val fullName = supabaseUser.userMetadata?.get("name")?.jsonPrimitive?.content ?: ""
        val firstName = fullName.split(" ").firstOrNull()?.lowercase() ?: "user"
        val randomNum = (100..999).random()
        val generatedUserName = "$firstName$randomNum"

        if (!existingUserIds.contains(supabaseUser.id)) {
            val newUser = User(
                userId = supabaseUser.id,
                name = fullName,
                userName = generatedUserName,
                bio = "Add A Bio",
                avatar = supabaseUser.userMetadata?.get("avatar_url")?.jsonPrimitive?.content ?:
                "https://dfopdqypqyqnrgpbjkpq.supabase.co/storage/v1/object/public/users-avatar//user1_profile.jpg"
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
                        set("avatar", user.avatar)
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



