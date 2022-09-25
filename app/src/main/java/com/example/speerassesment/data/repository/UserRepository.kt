package com.example.speerassesment.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.speerassesment.data.api.RetrofitClient
import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.model.UserDetailResponse
import com.example.speerassesment.data.repository.paging.FollowerPagingSource
import com.example.speerassesment.data.repository.paging.FollowingPageSource
import com.example.speerassesment.data.repository.paging.SearchPagingSource
import com.example.speerassesment.helper.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val isApiSuccessful = SingleLiveEvent<Boolean>()
    private val userDetailLiveData = SingleLiveEvent<UserDetailResponse>()

    //to search user by query
    fun searchUsers(query: String) = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { SearchPagingSource(RetrofitClient.apiInstance, query) }
    ).liveData

    //livedata to check if api was successful
    fun getIsApiSuccessful(): SingleLiveEvent<Boolean> {
        return isApiSuccessful
    }

    //to get user details using username
    fun getUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    isApiSuccessful.postValue(response.isSuccessful)
                    if (response.isSuccessful) {
                        userDetailLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    isApiSuccessful.postValue(false)
                }
            })
    }

    //live data of user details
    fun getUserDetailData(): SingleLiveEvent<UserDetailResponse> {
        return userDetailLiveData
    }

    //get followers followings list based on following flag
    fun getConnectionList(userName: String, following: Boolean): LiveData<PagingData<User>> {
        return if (following) {
            getFollowingList(userName)
        } else {
            getFollowerList(userName)
        }
    }

    //to get followers list
    private fun getFollowerList(query: String) = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { FollowerPagingSource(RetrofitClient.apiInstance, query) }
    ).liveData

    //to get following list
    private fun getFollowingList(query: String) = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { FollowingPageSource(RetrofitClient.apiInstance, query) }
    ).liveData
}