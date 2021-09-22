package com.dicoding.moviejetpacklast.di.home

import com.dicoding.moviejetpacklast.ui.fragment.FavoriteMovieFragment
import com.dicoding.moviejetpacklast.ui.fragment.FavoriteTvFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFavoriteMovieFragment(): FavoriteMovieFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteTvFragment(): FavoriteTvFragment
}