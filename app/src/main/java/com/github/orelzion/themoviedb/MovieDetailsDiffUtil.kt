package com.github.orelzion.themoviedb

import androidx.recyclerview.widget.DiffUtil
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity

class MovieDetailsDiffUtil : DiffUtil.ItemCallback<MovieDetailsEntity>() {
    override fun areItemsTheSame(oldItem: MovieDetailsEntity, newItem: MovieDetailsEntity): Boolean {
        return oldItem.title == newItem.title && oldItem.release_date == newItem.release_date
    }

    override fun areContentsTheSame(oldItem: MovieDetailsEntity, newItem: MovieDetailsEntity): Boolean {
        return oldItem == newItem
    }
}