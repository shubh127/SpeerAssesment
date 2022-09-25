package com.example.speerassesment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.repository.UserRepository
import com.example.speerassesment.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectionListViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {


    fun getConnectionsListData(): SingleLiveEvent<ArrayList<User>> {
        return repository.getUserProfilesList()
    }

    fun getIsApiSuccessful(): SingleLiveEvent<Boolean> {
        return repository.getIsApiSuccessful()
    }

    fun getConnectionList(userName: String, isFollowing: Boolean) {
        repository.getConnectionList(userName, isFollowing)
    }
}