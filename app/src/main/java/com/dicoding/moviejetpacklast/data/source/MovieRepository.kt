package com.dicoding.moviejetpacklast.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import com.dicoding.moviejetpacklast.data.source.remote.RemoteDataSource
import com.dicoding.moviejetpacklast.data.source.response.ApiResponse
import com.dicoding.moviejetpacklast.data.source.response.MovieResponse
import com.dicoding.moviejetpacklast.data.source.response.TvResponse
import com.dicoding.moviejetpacklast.data.source.room.LocalSource
import com.dicoding.moviejetpacklast.data.source.room.MovieDao
import com.dicoding.moviejetpacklast.data.source.status.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localSource: LocalSource): MovieDataSource{
    companion object {
        private var INSTANCE: MovieRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, localSource: LocalSource): MovieRepository {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(remoteDataSource, localSource)
            }
            return INSTANCE as MovieRepository
        }
    }
    override fun getPopularMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(){
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localSource.getListMovies(), config).build()
            }
            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                    data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                    remoteDataSource.getPopularMovies()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (item in data) {
                    val movie = MovieEntity(
                            null,
                            item.id,
                            item.name,
                            item.desc,
                            item.poster,
                            item.imgPreview,
                            false
                    )
                    movieList.add(movie)
                }
                localSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getListPopularMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localSource.getListFavoriteMovies(), config).build()
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieEntity> = localSource.getDetailMovie(movieId)

    override fun getPopularTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvResponse>>() {
            public override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localSource.getListTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>?): Boolean =
                    data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> =
                    remoteDataSource.getPopularTv()


            public override fun saveCallResult(data: List<TvResponse>) {
                val tvShowList = ArrayList<TvEntity>()
                for (item in data) {
                    val tvShow = TvEntity(
                            null,
                            item.id,
                            item.name,
                            item.desc,
                            item.poster,
                            item.imgPreview,
                            false
                    )
                    tvShowList.add(tvShow)
                }

                localSource.insertTv(tvShowList)
            }

        }.asLiveData()
    }

    override fun getListPopularTv(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localSource.getListFavoriteTv(), config).build()
    }

    override fun getTvDetail(tvShowId: Int):LiveData<TvEntity> = localSource.getDetailTv(tvShowId)

    override fun setFavoriteMovie(movie: MovieEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            localSource.setFavoriteMovie(movie)
        }
    }

    override fun setFavoriteTv(tvShow: TvEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            localSource.setFavoriteTv(tvShow)
        }
    }
}