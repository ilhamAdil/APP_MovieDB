package com.dicoding.moviejetpacklast.di.home

import android.app.Application
import com.dicoding.moviejetpacklast.data.source.MovieRepository
import com.dicoding.moviejetpacklast.data.source.remote.ApiService
import com.dicoding.moviejetpacklast.data.source.remote.RemoteDataSource
import com.dicoding.moviejetpacklast.data.source.room.LocalSource
import com.dicoding.moviejetpacklast.data.source.room.MovieDao
import com.dicoding.moviejetpacklast.data.source.room.MovieDatabase
import com.dicoding.moviejetpacklast.viewmodelfactory.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {

        @Singleton
        @Provides
        fun provideMovieDatabase(application: Application): MovieDatabase =
                MovieDatabase.getInstance(application)

        @Singleton
        @Provides
        fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao =
                movieDatabase.movieDao()

        @Singleton
        @Provides
        fun provideLocalSource(movieDao: MovieDao): LocalSource =
                LocalSource(movieDao)

        @Singleton
        @Provides
        fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
                RemoteDataSource(apiService)

        @Singleton
        @Provides
        fun provideMovieRepository(
                remoteDataSource: RemoteDataSource,
                localDataSource: LocalSource
        ): MovieRepository = MovieRepository(remoteDataSource, localDataSource)

        @Singleton
        @Provides
        fun provideViewModelFactory(catalogRepository: MovieRepository): ViewModelFactory =
                ViewModelFactory(catalogRepository)

    }
}