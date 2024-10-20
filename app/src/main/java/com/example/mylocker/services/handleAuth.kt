package com.example.mylocker.services

import com.example.mylocker.model.SignInRequest
import com.example.mylocker.model.SignInResponse
import com.example.mylocker.model.SignUpRequest
import com.example.mylocker.provider.directus.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

suspend fun signUp(username: String, password: String, first_name: String, last_name:String): Pair<Int, String?> {
    return withContext(Dispatchers.IO) {
        try {
            val request = SignUpRequest(username, password,first_name,last_name)
            val response = RetrofitInstance.api.signUp(request)

            if (response.isSuccessful) {
                // Retourner le code de statut et un message (ou rien si non nécessaire)
                Pair(response.code(), response.body()?.string())
            } else {
                // Retourner le code de statut et un message d'erreur
                Pair(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(500, e.message) // Retourner un code d'erreur 500 en cas d'exception
        }
    }
}

suspend fun signIn(email: String, password: String): Pair<Int, SignInResponse?> {
    return withContext(Dispatchers.IO) {
        try {
            val request = SignInRequest(email, password)
            val response = RetrofitInstance.api.signIn(request)
            val gson = Gson()
            val apiResponse = gson.fromJson(response.body().toString(), SignInResponse::class.java)


            if (response.isSuccessful) {
                // Retourner le code de statut et un message (ou rien si non nécessaire)

                val signInResponse = response.body()
                Pair(response.code(), signInResponse)
            } else {
                // Retourner le code de statut et un message d'erreur
                Pair(response.code(), null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(500, null) // Retourner un code d'erreur 500 en cas d'exception
        }
    }
}

