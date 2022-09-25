package com.example.speerassesment.data.model

data class UserDetailResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val company: String,
    val location: String,
    val email: String,
    val name: String,
    val following: Int,
    val followers: Int,
    val bio: String,
    val public_repos: Int
)
