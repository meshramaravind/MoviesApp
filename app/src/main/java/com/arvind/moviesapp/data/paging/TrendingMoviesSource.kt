package com.arvind.moviesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arvind.moviesapp.data.remote.MoviesAPI
import com.arvind.moviesapp.domain.models.Film
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingMoviesSource @Inject constructor(
    private val moviesAPI: MoviesAPI,
) : PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val nextPage = params.key ?: 1
            val trendingFilms =
                moviesAPI.getTrendingTodayMovies(page = nextPage)

            LoadResult.Page(
                data = trendingFilms.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (trendingFilms.results.isEmpty()) null else trendingFilms.page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}