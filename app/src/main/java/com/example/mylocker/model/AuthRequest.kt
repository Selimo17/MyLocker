package com.example.mylocker.model

data class SignUpRequest(val email: String, val password: String,val first_name: String,val last_name: String)
data class SignInRequest(val email: String, val password: String)
data class SignInResponse(val signInData: SignInData)
data class SignInData(val expires: Long,val refresh_token: String,val access_token: String)





