package com.dicoding.moviejetpacklast.ui.tv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviejetpacklast.R
import com.dicoding.moviejetpacklast.data.source.entity.TvEntity
import com.dicoding.moviejetpacklast.data.source.status.Status
import com.dicoding.moviejetpacklast.ui.DetailActivity
import com.dicoding.moviejetpacklast.ui.homestart.HomeViewModel
import com.dicoding.moviejetpacklast.utils.StringConst
import com.dicoding.moviejetpacklast.viewmodelfactory.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject

class TvFragment : DaggerFragment(), TvCallback {

    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        activity?.let { setupViewModel(it) }
        observeListTvShow()

    }

    private fun setupRecyclerView() {
        rv_tvshow.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TvAdapter(this@TvFragment)
        }
    }

    private fun setupViewModel(fragmentActivity: FragmentActivity) {
        fragmentActivity.let {
            viewModel = ViewModelProvider(
                    it,
                    factory
            )[HomeViewModel::class.java]
        }
    }

    private fun observeListTvShow() {
        viewModel.getListPopularTv().observe(viewLifecycleOwner, Observer { listTvShow ->
            if (listTvShow != null) {
                when (listTvShow.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        rv_tvshow.adapter?.let { adapter ->
                            when (adapter) {
                                is TvAdapter -> {
                                    adapter.submitList(listTvShow.data)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
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
