package com.nadikarim.submision1.data.model

data class UserSession(
    val name: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean
)