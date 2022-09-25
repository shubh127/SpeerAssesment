package com.example.speerassesment.data.model

import androidx.annotation.Keep

//response model for search users list api

@Keep
data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)




