package com.dicoding.moviejetpacklast.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.moviejetpacklast.data.source.MovieRepository
import com.dicoding.moviejetpacklast.ui.DetailViewModel
import com.dicoding.moviejetpacklast.ui.fragment.FavoriteViewModel
import com.dicoding.moviejetpacklast.ui.homestart.HomeViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val mMovieRepository: MovieRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}