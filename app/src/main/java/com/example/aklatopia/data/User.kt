package com.example.aklatopia.data

import com.example.aklatopia.SupabaseClient
import io.github.jan.supabase.gotrue.auth

data class User(
    val userId: String = "",
    val name: String = "",
    val userName: String = "",
    val bio: String = "",
    val avatar: String = ""
)

val supabaseUser = SupabaseClient.client.auth.currentUserOrNull()

//val user = if (supabaseUser != null) {
//    User(
//        userId = supabaseUser.id,
//        name = supabaseUser.userMetadata?.get("full_name")?.toString() ?: "",
//        userName = "", // let user fill this in your UI
//        bio = "",
//        avatar = supabaseUser.userMetadata?.get("avatar_url")?.toString() ?: ""
//    )
//} else null

val user = User(
    userId = "user1",
    name = "Matthew Molina",
    userName = "@posahh",
    bio = "“hindi mahalagang magwagi, aaaaaaaaaaaaaaa” - Lebron James",
    avatar = "https://res.cloudinary.com/dzdivpj68/image/upload/user_profile_pic_sdeooq.png"
)
