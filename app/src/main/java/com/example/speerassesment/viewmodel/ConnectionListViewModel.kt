package com.example.speerassesment.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.speerassesment.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectionListViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {

    private val state: SavedStateHandle = SavedStateHandle()
    private val userName = state.getLiveData(USERNAME, "")
    private var isFollowing = false

    //api call
    val connectionsResponse = userName.switchMap { queryString ->
        repository.getConnectionList(queryString, isFollowing).cachedIn(viewModelScope)
    }

    //data setter via view
    fun getData(query: String, isFollowing: Boolean) {
        userName.value = query
        this.isFollowing = isFollowing
    }

    companion object {
        private const val USERNAME = "username"
    }
}