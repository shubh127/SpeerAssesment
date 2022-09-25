package com.example.speerassesment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.speerassesment.data.model.User
import com.example.speerassesment.data.repository.UserRepository
import com.example.speerassesment.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    fun searchUsers(input: String) {
        repository.searchUsers(input)
    }

    fun getUserProfilesList(): SingleLiveEvent<ArrayList<User>> {
        return repository.getUserProfilesList()
    }

    fun getIsSearchSuccessful(): SingleLiveEvent<Boolean> {
        return repository.getIsApiSuccessful()
    }
}