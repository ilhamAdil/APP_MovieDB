package com.dicoding.moviejetpacklast.data.source.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM tb_favorite_movie")
    fun getListMovies() : DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tb_favorite_tvshow")
    fun getListTv() : DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tb_favorite_movie WHERE isFavorite = 1")
    fun getListFavoriteMovies() : DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tb_favorite_tvshow WHERE isFavorite = 1")
    fun getListFavoriteTv() : DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tb_favorite_movie WHERE movieId = :movieId")
    fun getDetailMovieById(movieId: Int) : LiveData<MovieEntity>

    @Query("SELECT * FROM tb_favorite_tvshow WHERE tvShowId = :tvShowId")
    fun getDetailTvById(tvShowId: Int) : LiveData<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieEntity::class)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TvEntity::class)
    fun insertTv(tvShows: List<TvEntity>)

    @Update(entity = MovieEntity::class)
    fun updateMovie(movie : MovieEntity)

    @Update(entity = TvEntity::class)
    fun updateTv(tvShows: TvEntity)

}