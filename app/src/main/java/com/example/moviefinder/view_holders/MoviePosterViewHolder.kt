package com.example.moviefinder.view_holders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R

class MoviePosterViewHolder(
    itemView: View,
    val mPosterImageView: ImageView = itemView.findViewById(R.id.iv_movie_poster)
) : RecyclerView.ViewHolder(itemView) {


}