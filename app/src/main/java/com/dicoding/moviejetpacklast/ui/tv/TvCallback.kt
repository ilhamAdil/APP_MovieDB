package com.dicoding.moviejetpacklast.ui.tv

import com.dicoding.moviejetpacklast.data.source.entity.TvEntity

interface TvCallback {
    fun onItemClicked(data: TvEntity)
}