package com.example.speerassesment.data.api

import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.model.UserDetailResponse
import com.example.speerassesment.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}