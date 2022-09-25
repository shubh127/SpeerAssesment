package com.example.speerassesment.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.speerassesment.data.api.Api
import com.example.speerassesment.data.model.User
import retrofit2.HttpException
import java.io.IOException

//paging source to achieve paging while fetching followings list from server

private const val START_PAGE_INDEX = 1

class FollowingPageSource(
    private val api: Api,
    private val username: String
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: START_PAGE_INDEX
        return try {
            val apiResponse = api.getFollowing(username, position, params.loadSize)

            LoadResult.Page(
                data = apiResponse,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (apiResponse.isEmpty()) null else position + 1
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