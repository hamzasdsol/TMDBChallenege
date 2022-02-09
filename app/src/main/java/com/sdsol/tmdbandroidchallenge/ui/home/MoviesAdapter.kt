package com.sdsol.tmdbandroidchallenge.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sdsol.tmdbandroidchallenge.R
import com.sdsol.tmdbandroidchallenge.databinding.LayoutItemMovieBinding
import com.sdsol.tmdbandroidchallenge.models.movieslistresponse.Result
import okhttp3.internal.notify

class MoviesAdapter(private val callback: (Int?) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    var moviesList: List<Result> = mutableListOf()

    class ViewHolder(val binding: LayoutItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_movie, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = moviesList[position]
        holder.binding.apply {
            result = dataItem
            this.root.setOnClickListener {
                callback.invoke(dataItem.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateData(data: List<Result>) {
        moviesList = data
        notifyDataSetChanged()
    }
}