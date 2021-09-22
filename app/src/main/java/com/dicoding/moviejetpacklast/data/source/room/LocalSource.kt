package com.dicoding.moviejetpacklast.data.source.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import javax.inject.Inject

class LocalSource @Inject constructor(private val movieDao: MovieDao) {

    fun getListMovies() : DataSource.Factory<Int, MovieEntity> = movieDao.getListMovies()

    fun getListFavoriteMovies() : DataSource.Factory<Int, MovieEntity> = movieDao.getListFavoriteMovies()

    fun getListTv() : DataSource.Factory<Int, TvEntity> = movieDao.getListTv()

    fun getListFavoriteTv() : DataSource.Factory<Int, TvEntity> = movieDao.getListFavoriteTv()

    fun getDetailMovie(movieId: Int) : LiveData<MovieEntity> = movieDao.getDetailMovieById(movieId)

    fun getDetailTv(tvShowId: Int) : LiveData<TvEntity> = movieDao.getDetailTvById(tvShowId)

    fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun insertTv(tvShows: List<TvEntity>) = movieDao.insertTv(tvShows)

    fun setFavoriteMovie(movie : MovieEntity) {
        movie.isFavorite = !movie.isFavorite
        movieDao.updateMovie(movie)
    }

    fun setFavoriteTv(tvShow : TvEntity) {
        tvShow.isFavorite = !tvShow.isFavorite
        movieDao.updateTv(tvShow)
    }
}