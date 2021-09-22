package com.dicoding.moviejetpacklast.di.home

import com.dicoding.moviejetpacklast.ui.DetailActivity
import com.dicoding.moviejetpacklast.ui.homestart.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity
}