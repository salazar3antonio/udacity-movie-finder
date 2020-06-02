package com.example.moviefinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviefinder.R
import com.example.moviefinder.databinding.FragmentMovieDetailsBinding
import com.example.moviefinder.utils.buildMovieBackDropUri
import com.example.moviefinder.utils.buildMoviePosterUri
import com.example.moviefinder.view_models.MovieViewModel

class MovieDetailsFragment : Fragment() {

    private val mMovieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //get the Movie argument passed into this Fragment
        val selectedMovie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).selectedMovie

        val binding: FragmentMovieDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        //Bind the Movie Object to the Data > Variable that is in the XML layout
        binding.movie = selectedMovie

        val posterUri = buildMoviePosterUri(selectedMovie.poster)
        val backdropUri = buildMovieBackDropUri(selectedMovie.backdrop)

        Glide.with(binding.root).load(posterUri).into(binding.ivPosterThumb)
        Glide.with(binding.root).load(backdropUri).into(binding.ivBackdrop)

        if (selectedMovie.isWantToWatch) {
            binding.cbWantToWatch.isChecked = true
        }

        binding.cbWantToWatch.setOnCheckedChangeListener { _, b ->
            if (b) {
                selectedMovie.isWantToWatch = true
                mMovieViewModel.update(selectedMovie)
            } else {
                selectedMovie.isWantToWatch = false
                mMovieViewModel.update(selectedMovie)
            }
        }

        return binding.root


    }
}