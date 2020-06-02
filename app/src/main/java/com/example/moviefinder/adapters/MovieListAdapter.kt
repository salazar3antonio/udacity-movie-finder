package com.example.moviefinder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefinder.R
import com.example.moviefinder.database.model.Movie
import com.example.moviefinder.fragments.NowPlayingFragmentDirections
import com.example.moviefinder.utils.buildMoviePosterUri
import com.example.moviefinder.view_holders.MoviePosterViewHolder

class MovieListAdapter(context: Context) :
    RecyclerView.Adapter<MoviePosterViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    var mMoviesList: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewHolder {

        val view = mInflater.inflate(R.layout.list_item_poster, parent, false)

        return MoviePosterViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mMoviesList.size
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        val movie = mMoviesList[position]
        val posterUri = buildMoviePosterUri(movie.poster)

        Glide.with(holder.itemView).load(posterUri).into(holder.mPosterImageView)

        holder.itemView.setOnClickListener {
            val movieDetailsAction =
                NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieDetailsFragment(movie)
            it.findNavController().navigate(movieDetailsAction)
        }

    }




}