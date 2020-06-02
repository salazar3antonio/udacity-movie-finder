package com.example.moviefinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R
import com.example.moviefinder.adapters.MovieListAdapter
import com.example.moviefinder.databinding.FragmentNowPlayingBinding
import com.example.moviefinder.view_models.MovieViewModel

class NowPlayingFragment : Fragment() {

    private val mMovieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private lateinit var mNowPlayingListAdapter: MovieListAdapter
    private lateinit var mWantToWatchListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMovieViewModel.callNowPlayingAPI()
        mNowPlayingListAdapter = MovieListAdapter(requireContext())
        mWantToWatchListAdapter = MovieListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentNowPlayingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)

        binding.lifecycleOwner = this

        val nowPlayingRecyclerView = binding.rvNowPlayingList
        nowPlayingRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        nowPlayingRecyclerView.adapter = mNowPlayingListAdapter

        val wantToWatchRecyclerView = binding.rvWantToWatch
        wantToWatchRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        wantToWatchRecyclerView.adapter = mWantToWatchListAdapter

        mMovieViewModel.mAllNowPlayingMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies.let {
                mNowPlayingListAdapter.mMoviesList = it
            }
        })

        mMovieViewModel.mAllWantToWatchMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies.let {
                mWantToWatchListAdapter.mMoviesList = it
            }
        })

        return binding.root


    }

}