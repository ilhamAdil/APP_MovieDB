package com.dicoding.moviejetpacklast.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bumptech.glide.load.engine.Resource
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity

interface MovieDataSource {
    fun getPopularMovies(): LiveData<com.dicoding.moviejetpacklast.data.source.status.Resource<PagedList<MovieEntity>>>

    fun getListPopularMovies(): LiveData<PagedList<MovieEntity>>

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity>

    fun getPopularTv(): LiveData<com.dicoding.moviejetpacklast.data.source.status.Resource<PagedList<TvEntity>>>

    fun getListPopularTv(): LiveData<PagedList<TvEntity>>

    fun getTvDetail(tvShowId: Int): LiveData<TvEntity>

    fun setFavoriteMovie(movie: MovieEntity)

    fun setFavoriteTv(tvShow: TvEntity)
}