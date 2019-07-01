package com.github.orelzion.themoviedb.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MovieDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int?,
    val id: Int,
    val title: String,
    val vote_count: Long,
    val vote_average: Double,
    val popularity: Double,
    val poster_path: String?,
    val genre_ids: List<Int>,
    val overview: String,
    val release_date: Date
)