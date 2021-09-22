package com.dicoding.moviejetpacklast.data.source.remote

import com.dicoding.moviejetpacklast.BuildConfig
import com.dicoding.moviejetpacklast.data.source.response.ListResponse
import com.dicoding.moviejetpacklast.data.source.response.MovieResponse
import com.dicoding.moviejetpacklast.data.source.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.MOVDB_API_KEY
    ) : Call<ListResponse<MovieResponse>>

    @GET("tv/on_the_air")
    fun getPopularTv(
        @Query("api_key") apiKey: String = BuildConfig.MOVDB_API_KEY
    ) : Call<ListResponse<TvResponse>>

}