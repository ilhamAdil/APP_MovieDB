package com.dicoding.moviejetpacklast.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.moviejetpacklast.BuildConfig
import com.dicoding.moviejetpacklast.R
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import com.dicoding.moviejetpacklast.utils.StringConst
import com.dicoding.moviejetpacklast.utils.StringConst.TYPE_MOVIE
import com.dicoding.moviejetpacklast.utils.StringConst.TYPE_TVSHOW
import com.dicoding.moviejetpacklast.utils.loadFromUrl
import com.dicoding.moviejetpacklast.viewmodelfactory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_bar.setExpandedTitleColor(Color.TRANSPARENT)

        viewModel = ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        val id = intent.getIntExtra(EXTRA_DATA, 0)
        val type = intent.getStringExtra(EXTRA_TYPE)

        if (type.equals(TYPE_MOVIE, ignoreCase = true)) {
            setupToolbarTitle(resources.getString(R.string.detail_movie))
            viewModel.getMovieDetail(id).observe(this, Observer {
                displayData(it, null)
            })

        } else if (type.equals(TYPE_TVSHOW, ignoreCase = true)) {
            setupToolbarTitle(resources.getString(R.string.detail_tv))
            viewModel.getTvDetail(id).observe(this, Observer {
                it?.let {
                    displayData(null, it)
                }
            })
        }
    }

    private fun displayData(movie: MovieEntity?, tvShow: TvEntity?) {
        val urlImage = movie?.poster ?: tvShow?.poster
        val urlHighlight = movie?.imgPreview ?: tvShow?.imgPreview
        val statusFavorite = movie?.isFavorite ?: tvShow?.isFavorite

        tv_detail_name.text = movie?.name ?: tvShow?.name
        tv_desc.text = movie?.desc ?: tvShow?.desc

        statusFavorite?.let { status ->
            setFavoriteState(status)
        }

        img_poster.loadFromUrl(BuildConfig.BASE_URL_IMAGE + StringConst.ENDPOINT_POSTER_SIZE_W185 + urlImage)
        img_huge.loadFromUrl(BuildConfig.BASE_URL_IMAGE + StringConst.ENDPOINT_POSTER_SIZE_W780 + urlHighlight)

        bt_favorite.setOnClickListener {
            setFavorite(movie, tvShow)
        }
    }


    private fun setFavorite(movie: MovieEntity?, tvShow: TvEntity?) {
        if (movie != null) {
            if (movie.isFavorite){
                showActionSnackBar("${movie.name} Removed from favorite")
            }else {
                showActionSnackBar("${movie.name} Added to favorite")
            }
            viewModel.setFavoriteMovie(movie)
        } else {
            if (tvShow != null) {
                if (tvShow.isFavorite){
                    showActionSnackBar("${tvShow.name} Removed from favorite")
                }else {
                    showActionSnackBar("${tvShow.name} Removed from favorite")
                }
                viewModel.setFavoriteTv(tvShow)
            }
        }
    }

    private fun showActionSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setupToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun setFavoriteState(status: Boolean){
        if (status) {
            bt_favorite.setImageResource(R.drawable.ic_favorite_true)
        } else {
            bt_favorite.setImageResource(R.drawable.ic_favorite_false)
        }
    }
}
