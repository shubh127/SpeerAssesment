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
class SearchViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val state: SavedStateHandle = SavedStateHandle()
    private val currentQuery = state.getLiveData(CURRENT_QUERY, "")

    val searchResponse = currentQuery.switchMap { queryString ->
        repository.searchUsers(queryString).cachedIn(viewModelScope)
    }

    fun search(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
    }
}