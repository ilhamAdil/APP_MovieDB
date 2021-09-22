package com.dicoding.moviejetpacklast.di.home

import com.dicoding.moviejetpacklast.ui.fragment.FavoriteFragment
import com.dicoding.moviejetpacklast.ui.movie.MovieFragment
import com.dicoding.moviejetpacklast.ui.tv.TvFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeTvFragment(): TvFragment

    @ContributesAndroidInjector(modules = [FavoriteFragmentBuildersModule::class])
    abstract fun contributeFavoriteFragment(): FavoriteFragment

}