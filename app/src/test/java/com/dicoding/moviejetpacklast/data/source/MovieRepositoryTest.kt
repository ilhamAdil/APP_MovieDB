package com.dicoding.moviejetpacklast.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import com.dicoding.moviejetpacklast.data.source.remote.RemoteDataSource
import com.dicoding.moviejetpacklast.data.source.room.LocalSource
import com.dicoding.moviejetpacklast.data.source.status.Resource
import com.dicoding.moviejetpacklast.utils.DataDummy
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import org.mockito.Mockito.mock


class MovieRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)


    private val listMovie = DataDummy.generateDataMovieDummy()
    private val listTvShow = DataDummy.generateDataTvShowDummy()

    private val movie = DataDummy.generateDataMovieDummy()[0]
    private val tvShow = DataDummy.generateDataTvShowDummy()[0]

    private val local = mock(LocalSource::class.java)
    private val movieRepository = FakeMovieRepository(remote, local)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getPopularMovies() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getListMovies()).thenReturn(dataSourceFactory)
        movieRepository.getPopularMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovieDummy()))
        Mockito.verify(local).getListMovies()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getPopularTv() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        Mockito.`when`(local.getListTv()).thenReturn(dataSourceFactory)
        movieRepository.getPopularTv()

        val tvShowEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTvShowDummy()))
        Mockito.verify(local).getListTv()
        assertNotNull(tvShowEntity.data)
        assertEquals(listTvShow.size.toLong(), tvShowEntity.data?.size?.toLong())
    }


    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = movie
        Mockito.`when`(local.getDetailMovie(movie.movieId)).thenReturn(dummyMovie)

        val data = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movie.movieId))
        Mockito.verify(local).getDetailMovie(movie.movieId)
        assertNotNull(data)
        assertEquals(movie.movieId, data.movieId)
    }

    @Test
    fun getTvDetail() {
        val dummyTvShow = MutableLiveData<TvEntity>()
        dummyTvShow.value = tvShow
        Mockito.`when`(local.getDetailTv(tvShow.tvShowId)).thenReturn(dummyTvShow)

        val data = LiveDataTestUtil.getValue(movieRepository.getTvDetail(tvShow.tvShowId))
        Mockito.verify(local).getDetailTv(tvShow.tvShowId)
        assertNotNull(data)
        assertEquals(tvShow.tvShowId, data.tvShowId)
    }

    @Test
    fun getListPopularMovies() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        Mockito.`when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        movieRepository.getListPopularMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovieDummy()))
        Mockito.verify(local).getListFavoriteMovies()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getListPopularTv() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        Mockito.`when`(local.getListFavoriteTv()).thenReturn(dataSourceFactory)
        movieRepository.getListPopularTv()

        val tvShowEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTvShowDummy()))
        Mockito.verify(local).getListFavoriteTv()
        assertNotNull(tvShowEntity.data)
        assertEquals(listTvShow.size.toLong(), tvShowEntity.data?.size?.toLong())
    }

    @Test
    fun setFavoriteMovie() = runBlocking {
        val dataDummy = DataDummy.generateDataMovieDummy()[0].copy(isFavorite = false)
        doNothing().`when`(local).setFavoriteMovie(dataDummy)
        movieRepository.setFavoriteMovie(dataDummy)
        verify(local, times(1)).setFavoriteMovie(dataDummy)
    }
}

