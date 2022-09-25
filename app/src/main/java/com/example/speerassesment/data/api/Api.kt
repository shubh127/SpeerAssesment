package com.example.speerassesment.data.api

import com.example.speerassesment.data.model.SearchResponse
import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.model.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    //getSearchResponse method to get results on searching via username
    //Q is the searched text
    //page is the number of page
    //per_page is the number of items per page

    @GET("search/users")
    suspend fun getSearchResponse(
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResponse

    //getUserDetail method to get a user's detail
    //username is passed for the user whose details to be fetched

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    //getFollowers method to get list of followers of a user
    //username is the name of user whose followers are to be fetched
    //page is the number of page
    //per_page is the number of items per page

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<User>

    //getFollowing method to get list of following of a user
    //username is the name of user whose following are to be fetched
    //page is the number of page
    //per_page is the number of items per page

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<User>
}