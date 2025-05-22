package com.example.aklatopia

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aklatopia.SupabaseClient.uploadImageToSupabase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.UUID
import kotlin.Result // For the Result class
import kotlin.Result.Companion.success
import kotlin.time.Duration.Companion.days

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

    suspend fun uploadImageToSupabase(
        context: Context,
        bucketName: String,
        path: String,
        uri: Uri,
        maxFileSizeKB: Long = 1024 // 1MB default
    ): Result<String> {
        return try {
            // 1. Convert URI to byte array with size check
            val bytes = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                ByteArrayOutputStream().use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    var totalBytes = 0L

                    while (inputStream.read(buffer).also { length = it } != -1) {
                        totalBytes += length
                        if (totalBytes > maxFileSizeKB * 1024) {
                            throw IllegalArgumentException("File size exceeds $maxFileSizeKB KB limit")
                        }
                        outputStream.write(buffer, 0, length)
                    }
                    outputStream.toByteArray()
                }
            } ?: return Result.failure(IllegalStateException("Could not read file"))

            // 2. Upload to Supabase Storage
            client.storage.from(bucketName).upload(
                path = path,
                data = bytes,
                upsert = true // Overwrite if exists
            )

            // 3. Get public URL
            val publicUrl = client.storage
                .from(bucketName)
                .createSignedUrl(path, 7.days) // 1 week expiry
                .toString()

            Result.success(publicUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

class ImageUploadViewModel : ViewModel() {
    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState

    sealed class UploadState {
        object Idle : UploadState()
        object Loading : UploadState()
        data class Success(val url: Any?) : UploadState()
        data class Error(val message: String) : UploadState()
    }

    fun uploadImage(context: Context, uri: Uri) {
        viewModelScope.launch {
            _uploadState.value = UploadState.Loading
            when (val result = uploadImageToSupabase(
                context = context,
                bucketName = "users-avatar", // Your bucket name
                path = "uploads/${UUID.randomUUID()}.jpg", // Unique filename
                uri = uri
            )) {
//                is Result.Success -> {
//                    _uploadState.value = UploadState.Success(result.value)
//                }
//                is Result.Failure -> {
//                    _uploadState.value = UploadState.Error(
//                        result.throwable.message ?: "Upload failed"
//                    )
//                }
            }
        }
    }
}