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
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import com.dicoding.moviejetpacklast.ui.DetailActivity
import com.dicoding.moviejetpacklast.ui.tv.TvAdapter
import com.dicoding.moviejetpacklast.ui.tv.TvCallback
import com.dicoding.moviejetpacklast.utils.StringConst
import com.dicoding.moviejetpacklast.utils.StringConst.TYPE_TVSHOW
import com.dicoding.moviejetpacklast.viewmodelfactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.empty_state.*
import kotlinx.android.synthetic.main.fragment_favorite_tv.*
import javax.inject.Inject

class FavoriteTvFragment : DaggerFragment(), TvCallback {

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        parentFragment?.let {
            viewModel = ViewModelProvider(it, factory)[FavoriteViewModel::class.java]
        }
        observeFavoriteTvShow()

    }

    private fun observeFavoriteTvShow() {
        viewModel.getListFavoriteTv().observe(viewLifecycleOwner, Observer {
            if (it != null){
                rv_favorite_tvshow.adapter?.let {adapter ->
                    when (adapter) {
                        is TvAdapter -> {
                            if (it.isNullOrEmpty()){
                                rv_favorite_tvshow.visibility = View.GONE
                                enableEmptyStateEmptyFavoriteTvShow()
                            } else {
                                rv_favorite_tvshow.visibility = View.VISIBLE
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
        rv_favorite_tvshow.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TvAdapter(this@FavoriteTvFragment)
        }
    }

    private fun enableEmptyStateEmptyFavoriteTvShow() {
        empty.text = resources.getString(R.string.fav_list)
        desc_empty.text =resources.getString(R.string.belum_ada_list)
        favorite_tvshow_empty_state.visibility = View.VISIBLE
    }

    override fun onItemClicked(data: TvEntity) {
        startActivity(
            Intent(
                context,
                DetailActivity::class.java
            )
                .putExtra(DetailActivity.EXTRA_DATA, data.tvShowId)
                .putExtra(DetailActivity.EXTRA_TYPE, StringConst.TYPE_TVSHOW)
        )
    }

}
