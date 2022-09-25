package com.example.speerassesment.data.repository

import com.example.speerassesment.data.api.RetrofitClient
import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.model.UserDetailResponse
import com.example.speerassesment.data.model.UserResponse
import com.example.speerassesment.helper.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val userListLiveData = SingleLiveEvent<ArrayList<User>>()
    private val isApiSuccessful = SingleLiveEvent<Boolean>()
    private val userDetailLiveData = SingleLiveEvent<UserDetailResponse>()


    fun searchUsers(searchedText: String) {
        RetrofitClient.apiInstance
            .getUsers(searchedText)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    isApiSuccessful.postValue(response.isSuccessful)
                    if (response.isSuccessful) {
                        userListLiveData.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    isApiSuccessful.postValue(false)
                }
            })
    }

    fun getUserProfilesList(): SingleLiveEvent<ArrayList<User>> {
        return userListLiveData
    }

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

    fun getConnectionList(userName: String, following: Boolean) {
        if (following) {
            getFollowingList(userName)
        } else {
            getFollowerList(userName)
        }
    }

    private fun getFollowerList(userName: String) {
        RetrofitClient.apiInstance
            .getFollowers(userName)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    isApiSuccessful.postValue(response.isSuccessful)
                    if (response.isSuccessful) {
                        userListLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    isApiSuccessful.postValue(false)
                }
            })
    }

    private fun getFollowingList(userName: String) {
        RetrofitClient.apiInstance
            .getFollowing(userName)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    isApiSuccessful.postValue(response.isSuccessful)
                    if (response.isSuccessful) {
                        userListLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    isApiSuccessful.postValue(false)
                }
            })
    }
}