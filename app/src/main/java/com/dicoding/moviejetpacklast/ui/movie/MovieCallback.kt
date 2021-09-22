package com.dicoding.moviejetpacklast.ui.movie

import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity

interface MovieCallback {
    fun onItemClicked(data: MovieEntity)
}