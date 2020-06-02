package com.example.moviefinder.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.database.model.Theater
import com.example.moviefinder.databinding.ListItemTheaterResultBinding

class TheaterResultViewHolder(private val theaterResultBinding: ListItemTheaterResultBinding) :
    RecyclerView.ViewHolder(theaterResultBinding.root) {

    fun bind(theater: Theater) {
        theaterResultBinding.theater = theater
    }

}