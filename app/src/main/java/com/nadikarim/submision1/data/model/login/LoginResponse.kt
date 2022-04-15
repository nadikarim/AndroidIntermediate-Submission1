package com.nadikarim.submision1.data.model.login

data class LoginResponse(
    val error: Boolean,
    val loginResult: LoginResult,
    val message: String
)