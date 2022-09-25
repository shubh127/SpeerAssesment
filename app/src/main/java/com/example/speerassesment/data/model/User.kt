package com.example.speerassesment.data.model

//model to deserialize the basic user information

data class User(
    val login: String,
    val id: Int,
    val avatar_url: String,
)