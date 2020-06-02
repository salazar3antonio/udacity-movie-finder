package com.example.moviefinder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R
import com.example.moviefinder.database.model.Theater
import com.example.moviefinder.databinding.ListItemTheaterResultBinding
import com.example.moviefinder.view_holders.TheaterResultViewHolder

class TheaterListAdapter(context: Context) :
    RecyclerView.Adapter<TheaterResultViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    var mTheaterList: List<Theater> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterResultViewHolder {

        val listItemTheaterResultBinding: ListItemTheaterResultBinding =
            DataBindingUtil.inflate(mInflater, R.layout.list_item_theater_result, parent, false)

        return TheaterResultViewHolder(listItemTheaterResultBinding)

    }

    override fun getItemCount(): Int {
        return mTheaterList.size
    }

    override fun onBindViewHolder(holder: TheaterResultViewHolder, position: Int) {

        val theater = mTheaterList[position]
        holder.bind(theater)

    }


}