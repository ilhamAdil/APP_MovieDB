package com.dicoding.moviejetpacklast.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.moviejetpacklast.data.source.MovieRepository
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun getListFavoriteMovie(): LiveData<PagedList<MovieEntity>> = movieRepository.getListPopularMovies()

    fun getListFavoriteTv(): LiveData<PagedList<TvEntity>> = movieRepository.getListPopularTv()
}