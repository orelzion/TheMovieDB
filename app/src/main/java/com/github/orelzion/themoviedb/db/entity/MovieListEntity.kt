package com.github.orelzion.themoviedb.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.github.orelzion.themoviedb.model.MovieCategory

@Entity
data class MovieListEntity(
    // An auto generated id
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    // Current page reached (so if we want to fetch more, we will know to ask for the next page)
    val page: Int,
    // The total number of pages available for this list
    val maxPage: Int,
    // The of movie ids
    val movieIdList: List<Int>
)