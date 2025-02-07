import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gurmeet.alllanguagenewsapp.data.api.NetworkService
import com.gurmeet.alllanguagenewsapp.data.model.headlines.HeadlineResponse
import retrofit2.HttpException
import java.io.IOException

class DynamicPagingSource<T : Any>(
    private val apiService: NetworkService,
    private val screenType: String,
    private val country: String?,
    additionalParam: String? = null
) : PagingSource<Int, T>() {

    companion object {
        private const val STARTING_PAGE = 1
        private const val PAGE_SIZE = 10
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: STARTING_PAGE

        /*return try {

            val responseData: List<T> = when (screenType) {
                "HEADLINES" -> apiService.getTopHeadlines(country!!, page, PAGE_SIZE) as HeadlineResponse).headlines as List<T>
             *//*   "POSTS" -> apiService.getPosts(page, PAGE_SIZE, country ?: "") as List<T>
                "COMMENTS" -> apiService.getComments(page, PAGE_SIZE, country?.toIntOrNull() ?: 0) as List<T>*//*
                else -> throw IllegalArgumentException("Invalid Screen Type: $screenType")





            }*/
        return try {
            val responseData: List<T> = when (screenType) {
                "HEADLINES" -> {
                    val response = apiService.getTopHeadlines(country!!, page, PAGE_SIZE)

                    Log.e("dsfsd", page.toString()+","+PAGE_SIZE.toString())
                    response.articles as List<T>  // Access headlines inside the response


                }
                else -> throw IllegalArgumentException("Invalid Screen Type: $screenType")
            }

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}