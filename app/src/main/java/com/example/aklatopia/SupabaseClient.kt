package com.example.aklatopia

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.aklatopia.data.User
import com.example.aklatopia.home.components.Bookz
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
//import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage

object SupabaseBooksData {
    val booksState = mutableStateListOf<Bookz>()
}

object FBFavBooks {
    val id = mutableStateListOf<Int>()
    val favoritesLoaded = mutableStateOf(false)
}

object SupabaseClient {
    private const val SUPABASE_URL = BuildConfig.SUPABASE_URL
    private const val SUPABASE_KEY = BuildConfig.SUPABASE_ANON_KEY

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Storage)
        }
    }

    suspend fun logout() {
        client.auth.signOut();
    }

    suspend fun signUpNewUser(email: String, password: String) : Boolean{
        return try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true

        } catch (e: Exception) {
            Log.e("Signup", "Error: ${e.message}")
            false
        }
    }

    suspend fun signInWithEmail(email: String, password: String): Boolean {
        return try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e("SupabaseAuth", "Login failed: ${e.message}")
            false
        }
    }

    suspend fun newUser(user: User) {
        try {
            val insertedUser = client
                .from("Users")
                .insert(listOf(user))
                .decodeSingle<User>()
            Log.d("Supabase", "User inserted: $insertedUser")
        } catch (e: Exception) {
            Log.e("Supabase", "Insert failed: ${e.message}")
        }
    }


    fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                input.readBytes()
            }
        } catch (e: Exception) {
            Log.e("Supabase", "Error reading URI: ${e.message}")
            null
        }
    }

    suspend fun uploadAvatar(context: Context, uri: Uri, path: String): Boolean {
        val bytes = uriToByteArray(context, uri) ?: return false

        return try {
            client.storage.from("users-avatar")
                .upload(path, bytes)
            true
        } catch (e: Exception) {
            Log.e("Supabase", "Upload failed: ${e.message}")
            false
        }
    }

}