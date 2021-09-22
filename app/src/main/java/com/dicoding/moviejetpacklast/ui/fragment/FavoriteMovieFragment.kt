package com.dicoding.moviejetpacklast.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviejetpacklast.R
import com.dicoding.moviejetpacklast.data.source.entity.MovieEntity
import com.dicoding.moviejetpacklast.ui.DetailActivity
import com.dicoding.moviejetpacklast.ui.movie.MovieAdapter
import com.dicoding.moviejetpacklast.ui.movie.MovieCallback
import com.dicoding.moviejetpacklast.utils.StringConst
import com.dicoding.moviejetpacklast.viewmodelfactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.empty_state.*
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import javax.inject.Inject

class FavoriteMovieFragment : DaggerFragment(), MovieCallback {

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        parentFragment?.let {
            viewModel = ViewModelProvider(it, factory)[FavoriteViewModel::class.java]
        }
        observeFavoriteMovies()

    }

    private fun observeFavoriteMovies() {
        viewModel.getListFavoriteMovie().observe(viewLifecycleOwner, Observer {
            if (it != null){
                rv_favorite_movie.adapter?.let {adapter ->
                    when (adapter) {
                        is MovieAdapter -> {
                            if (it.isNullOrEmpty()){
                                rv_favorite_movie.visibility = View.GONE
                                enableEmptyStateEmptyFavoriteMovie()
                            } else {
                                rv_favorite_movie.visibility = View.VISIBLE
                                adapter.submitList(it)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        })
    }
    private fun setupRecyclerView() {
        rv_favorite_movie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(this@FavoriteMovieFragment)
        }
    }

    private fun enableEmptyStateEmptyFavoriteMovie() {
        empty.text = resources.getString(R.string.fav_list)
        desc_empty.text =
            resources.getString(R.string.belum_ada_list)
        favorite_movie_empty_state.visibility = View.VISIBLE
    }

    override fun onItemClicked(data: MovieEntity) {
        startActivity(
            Intent(
                context,
                DetailActivity::class.java
            )
                .putExtra(DetailActivity.EXTRA_DATA, data.movieId)
                .putExtra(DetailActivity.EXTRA_TYPE, StringConst.TYPE_MOVIE)
        )
    }

}