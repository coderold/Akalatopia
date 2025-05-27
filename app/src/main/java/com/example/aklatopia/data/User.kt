package com.example.aklatopia.data

import com.example.aklatopia.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import kotlinx.serialization.json.jsonPrimitive

data class User(
    val userId: String = "",
    val name: String = "",
    var userName: String = "",
    var bio: String = "",
    val avatar: String = "",
)

val supabaseUser = SupabaseClient.client.auth.currentUserOrNull()

val user = if (supabaseUser != null) {

    User(
        userId = supabaseUser.id,
        name = supabaseUser.userMetadata?.get("name")?.jsonPrimitive?.content ?: "",
        userName = "", // let user fill this in your UI
        bio = "",
        avatar = supabaseUser.userMetadata?.get("avatar_url")?.jsonPrimitive?.content ?: ""
    )
} else {
    User(
        userId = "user1",
        name = "Matthew Molina",
        userName = "@posahh",
        bio = "“hindi mahalagang magwagi, aaaaaaaaaaaaaaa” - Lebron James",
        avatar = "https://res.cloudinary.com/dzdivpj68/image/upload/user_profile_pic_sdeooq.png"
    )
}