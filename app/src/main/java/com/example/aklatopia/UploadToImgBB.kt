package com.example.aklatopia

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.MutableState
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

fun uploadImageToImgbb(context: Context, imageUri: Uri, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(imageUri)

    if (inputStream != null) {
        val imageBytes = inputStream.readBytes()
        val encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("key", "61b2a5b1b4c41f12188cd7e66af08d05")
            .add("image", encodedImage)
            .build()

        val request = Request.Builder()
            .url("https://api.imgbb.com/1/upload")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError("Upload failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val json = JSONObject(responseBody!!)
                    val imageUrl = json.getJSONObject("data").getString("url")
                    onSuccess(imageUrl)
                } else {
                    onError("Upload failed with code: ${response.code}")
                }
            }
        })
    } else {
        onError("Could not read image file")
    }
}
