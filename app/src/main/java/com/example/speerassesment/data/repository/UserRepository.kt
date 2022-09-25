package com.example.speerassesment.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.speerassesment.data.api.RetrofitClient
import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.model.UserDetailResponse
import com.example.speerassesment.helper.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val userListLiveData = SingleLiveEvent<ArrayList<User>>()
    private val isApiSuccessful = SingleLiveEvent<Boolean>()
    private val userDetailLiveData = SingleLiveEvent<UserDetailResponse>()


    fun searchUsers(query: String) = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { SearchPagingSource(RetrofitClient.apiInstance, query) }
    ).liveData


    fun getIsApiSuccessful(): SingleLiveEvent<Boolean> {
        return isApiSuccessful
    }

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

    fun getUserDetailData(): SingleLiveEvent<UserDetailResponse> {
        return userDetailLiveData
    }

    fun getConnectionList(userName: String, following: Boolean): LiveData<PagingData<User>> {
        return if (following) {
            getFollowingList(userName)
        } else {
            getFollowerList(userName)
        }
    }

    private fun getFollowerList(query: String) = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { FollowerPagingSource(RetrofitClient.apiInstance, query) }
    ).liveData

    private fun getFollowingList(query: String) = Pager(
        config = PagingConfig(pageSize = 30, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { FollowingPageSource(RetrofitClient.apiInstance, query) }
    ).liveData
}