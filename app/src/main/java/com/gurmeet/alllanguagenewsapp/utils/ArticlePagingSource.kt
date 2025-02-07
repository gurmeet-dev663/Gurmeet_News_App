package com.gurmeet.alllanguagenewsapp.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.headlines.Article
import retrofit2.HttpException
import java.io.IOException

class ArticlePagingSource (
    private val apiService: NetworkService,
    private val country: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1 // Start from page 1
            val pageSize = params.loadSize

          //  val pageSize = minOf(params.loadSize, 10)

            val response = apiService.getTopHeadlines(country, page, pageSize)
            Log.e("PagingDebug", "✅ API Response: Page=$page, Items=${response.articles.size}")

              Log.e("dsfsd", page.toString()+","+pageSize.toString())
       //     Log.e("PagingDebug", "🔄 Loading Page: $page, PageSize: $pageSize") // Debug log
            Log.e("PagingDebug", "🔄 API Call: Page=$page, PageSize=$pageSize, LoadType=${params::class.java.simpleName}")

            val articles = response.articles


            if (response.articles.size < pageSize) {
                Log.e("PagingDebug", "⚠️ API Returned Less Than PageSize! Paging May Stop Early.")
            }

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )



        } catch (e: IOException) {
            LoadResult.Error(e) // Network Error
        } catch (e: HttpException) {
            LoadResult.Error(e) // API Error
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}