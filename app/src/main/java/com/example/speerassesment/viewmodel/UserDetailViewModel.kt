package com.example.speerassesment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.speerassesment.data.model.UserDetailResponse
import com.example.speerassesment.data.repository.UserRepository
import com.example.speerassesment.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {

    //api call
    fun getUserDetail(userName: String) {
        repository.getUserDetail(userName)
    }

    //live data from repo
    fun getUserDetailData(): SingleLiveEvent<UserDetailResponse> {
        return repository.getUserDetailData()
    }

    //api response via repo
    fun getIsDetailSuccessful(): SingleLiveEvent<Boolean> {
        return repository.getIsApiSuccessful()
    }

}