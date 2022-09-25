package com.example.speerassesment.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.speerassesment.data.api.Api
import com.example.speerassesment.data.model.User
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 1

class SearchPagingSource(
    private val searchApi: Api,
    private val query: String?
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: START_PAGE_INDEX
        return try {
            val apiResponse =
                searchApi.getSearchResponse(query, position, params.loadSize)
            val users = apiResponse.items

            LoadResult.Page(
                data = users,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (users.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}