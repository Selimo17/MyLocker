package com.example.mylocker.provider.directus

import com.example.mylocker.model.SignInRequest
import com.example.mylocker.model.SignInResponse
import com.example.mylocker.model.SignUpRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("users/register") // Change l'URL selon ton API
    suspend fun signUp(@Body request: SignUpRequest): Response<ResponseBody>

    @POST("auth/login") // Change l'URL selon ton API
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>
}
