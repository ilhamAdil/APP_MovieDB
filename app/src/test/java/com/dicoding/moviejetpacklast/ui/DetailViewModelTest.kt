package com.dicoding.moviejetpacklast.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.moviejetpacklast.data.source.MovieRepository
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import com.dicoding.moviejetpacklast.utils.DataDummy
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private val dummyMovie = DataDummy.generateDataMovieDummy()[0]
    private val dummyTvShow = DataDummy.generateDataTvShowDummy()[0]

    private val movieId = dummyMovie.movieId
    private val tvId = dummyTvShow.tvShowId

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observerMovie: Observer<MovieEntity>

    @Mock
    private lateinit var observerTv: Observer<TvEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(movieRepository)
    }


    @Test
    fun getMovieDetail() {
        val movieDummy = MutableLiveData<MovieEntity>()
        movieDummy.value = dummyMovie

        Mockito.`when`(movieRepository.getMovieDetail(movieId)).thenReturn(movieDummy)

        val movieData = viewModel.getMovieDetail(movieId).value

        Assert.assertNotNull(movieData)
        assertEquals(dummyMovie.id, movieData?.id)
        assertEquals(dummyMovie.movieId, movieData?.movieId)
        assertEquals(dummyMovie.name, movieData?.name)
        assertEquals(dummyMovie.desc, movieData?.desc)
        assertEquals(dummyMovie.poster, movieData?.poster)
        assertEquals(dummyMovie.imgPreview, movieData?.imgPreview)

        viewModel.getMovieDetail(movieId).observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getTvDetail() {
        val tvDummy = MutableLiveData<TvEntity>()
        tvDummy.value = dummyTvShow

        Mockito.`when`(movieRepository.getTvDetail(tvId)).thenReturn(tvDummy)

        val tvShowData = viewModel.getTvDetail(tvId).value

        Assert.assertNotNull(tvShowData)
        assertEquals(dummyTvShow.id, tvShowData?.id)
        assertEquals(dummyTvShow.tvShowId, tvShowData?.tvShowId)
        assertEquals(dummyTvShow.name, tvShowData?.name)
        assertEquals(dummyTvShow.desc, tvShowData?.desc)
        assertEquals(dummyTvShow.poster, tvShowData?.poster)
        assertEquals(dummyTvShow.imgPreview, tvShowData?.imgPreview)

        viewModel.getTvDetail(tvId).observeForever(observerTv)
        verify(observerTv).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteMovie() {
        viewModel = DetailViewModel(movieRepository)
        doNothing().`when`(movieRepository).setFavoriteMovie(dummyMovie)
        viewModel.setFavoriteMovie(dummyMovie)
        verify(movieRepository, times(1)).setFavoriteMovie(dummyMovie)
    }
}